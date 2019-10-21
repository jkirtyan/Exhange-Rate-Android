package com.jkirtyan.exchangerate.ui.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.jkirtyan.exchangerate.ExchangeRateApplication.Companion.baseCurrencyCode
import com.jkirtyan.exchangerate.entity.ExchangeRateResponse
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ExchangeRateListViewModel {
    private val values = HashMap<String, ObservableField<String>>()
    private var currencyCodes = ArrayList<String>()

    var baseChangeAction = MutableLiveData<DiffUtil.DiffResult>().apply {
        value = null
    }

    var enteredValue: Double? = null
        set(value) {
            field = value
            updateValues()
        }

    var exchangeRate: ExchangeRateResponse? = null
        set(value) {
            field = value

            currencyCodes.clear()
            value?.rates?.keys?.toList()?.let { rates ->
                currencyCodes.addAll(rates)
                currencyCodes.sort()
                currencyCodes.add(0, baseCurrencyCode)
            }

            updateValues()
        }

    private fun updateValues() {
        currencyCodes.forEach { key ->
            exchangeRate?.rates?.get(key)?.let { value ->
                val calculatedValue = (enteredValue ?: 0.0) * value
                val calculatedValueText = String.format("%.2f", calculatedValue)

                values[key]?.set(calculatedValueText) ?: run {
                    values[key] = ObservableField(calculatedValueText)
                }
            }
        }

    }

    fun itemCount() = currencyCodes.size

    fun currencyCode(position: Int) = currencyCodes[position]
    fun currencyName(position: Int) = Currency.getInstance(currencyCode(position)).displayName
    fun value(position: Int) =
        if (position == 0) ObservableField<String>(enteredValue?.toString()) else values[currencyCode(
            position
        )]

    fun editable(position: Int) = position == 0

    fun onItemClicked(position: Int) {
        if (position > 0) {
            val lastCurrencyCodes = ArrayList(currencyCodes)

            baseCurrencyCode = currencyCodes[position]

            currencyCodes.remove(baseCurrencyCode)
            currencyCodes.sort()
            currencyCodes.add(0, baseCurrencyCode)

            updateValues()

            val calculateDiff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return lastCurrencyCodes[oldItemPosition] == currencyCodes[newItemPosition]
                }

                override fun getOldListSize(): Int {
                    return lastCurrencyCodes.size
                }

                override fun getNewListSize(): Int {
                    return currencyCodes.size
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return lastCurrencyCodes[oldItemPosition] == currencyCodes[newItemPosition]
                }
            })

            baseChangeAction.value = calculateDiff
        }
    }

    fun onBaseChangeFinished() {
        baseChangeAction.value = null
    }
}