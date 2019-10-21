package com.jkirtyan.exchangerate.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.jkirtyan.exchangerate.ExchangeRateApplication.Companion.baseCurrencyCode
import com.jkirtyan.exchangerate.ExchangeRateService
import com.jkirtyan.exchangerate.entity.ExchangeRateResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    @Inject lateinit var service: ExchangeRateService
    private var disposable: Disposable? = null

    var exchangeRate = MutableLiveData<ExchangeRateResponse>()

    @SuppressLint("CheckResult")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        Observable
            .interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    service.getExchangeRate(baseCurrencyCode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                if (it.isSuccessful) {
                                    exchangeRate.value = it.body()
                                } else {
                                    // TODO: handle it
                                }
                            },
                            Throwable::printStackTrace // TODO: handle it
                        )
                },
                Throwable::printStackTrace // TODO: handle it
            )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        disposable?.dispose()
    }
}