package com.jkirtyan.exchangerate.ui.viewmodel

import androidx.databinding.ObservableField
import com.jkirtyan.exchangerate.entity.ExchangeRateResponse
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ExchangeRateListViewModel {
    private val values = HashMap<String, ObservableField<String>>()

    private var currencyCodes = ArrayList<String>()

    var enteredValue: Double? = null
    set(value) {
        field = value
        updateValues()
    }

    var exchangeRate: ExchangeRateResponse? = null
        set(value) {
            field = value

            currencyCodes.clear()
            value?.rates?.keys?.toList()?.let {
                currencyCodes.add(value.base)
                currencyCodes.addAll(it)
            }

            updateValues()
        }

    private fun updateValues() {
        exchangeRate?.rates?.let { rates ->
            rates.iterator().forEach { entry ->
                val calculatedValue = String.format("%.2f", entry.value * (enteredValue ?: 0.0))

                values[entry.key]?.set(calculatedValue) ?: run {
                    values[entry.key] = ObservableField(calculatedValue)
                }
            }
        }
    }

    fun itemCount() = currencyCodes.size

    fun currencyCode(position: Int) = currencyCodes[position]
    fun currencyName(position: Int) = Currency.getInstance(currencyCode(position)).displayName
    fun value(position: Int) = if (position == 0) ObservableField<String>(enteredValue?.toString()) else values[currencyCode(position)]
    fun editable(position: Int) = position == 0
}