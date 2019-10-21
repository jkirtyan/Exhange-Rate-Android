package com.jkirtyan.exchangerate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jkirtyan.exchangerate.ExchangeRateApplication
import com.jkirtyan.exchangerate.R
import com.jkirtyan.exchangerate.ui.custom.adapter.ExchangeRateListAdapter
import com.jkirtyan.exchangerate.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var adapter: ExchangeRateListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        ExchangeRateApplication.appComponent.inject(this)

        lifecycle.addObserver(viewModel)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvExchangeRate.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvExchangeRate.adapter = adapter

        viewModel.exchangeRate.observe(this, Observer { exchangeRate ->
            adapter.exchangeRate = exchangeRate
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycle.removeObserver(viewModel)
        viewModel.exchangeRate.removeObservers(this)
    }
}
