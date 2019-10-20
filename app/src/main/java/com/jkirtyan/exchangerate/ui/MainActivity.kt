package com.jkirtyan.exchangerate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jkirtyan.exchangerate.ExchangeRateApplication
import com.jkirtyan.exchangerate.R
import com.jkirtyan.exchangerate.ui.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        ExchangeRateApplication.appComponent.inject(this)

        lifecycle.addObserver(viewModel)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.exchangeRate.observe(this, Observer { exchangeRate ->
            // TODO
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycle.removeObserver(viewModel)
        viewModel.exchangeRate.removeObservers(this)
    }
}
