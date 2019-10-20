package com.jkirtyan.exchangerate

import android.app.Application
import com.jkirtyan.exchangerate.di.AppComponent
import com.jkirtyan.exchangerate.di.DaggerAppComponent

class ExchangeRateApplication: Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()
    }
}