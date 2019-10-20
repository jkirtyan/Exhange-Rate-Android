package com.jkirtyan.exchangerate.ui.viewmodel

import androidx.lifecycle.*
import com.jkirtyan.exchangerate.entity.ExchangeRateResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    @Inject lateinit var exchangeRateCall: Single<Response<ExchangeRateResponse>>
    private var disposable: Disposable? = null

    var exchangeRate = MutableLiveData<ExchangeRateResponse>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        disposable = exchangeRateCall
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .delay(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .repeat()
            .subscribe (
                {
                    exchangeRate.value = it.body()
                },
                {
                    it.printStackTrace()
                }
            )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        disposable?.dispose()
    }
}