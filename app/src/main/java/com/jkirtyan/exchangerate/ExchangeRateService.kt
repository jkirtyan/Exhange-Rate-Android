package com.jkirtyan.exchangerate

import com.jkirtyan.exchangerate.entity.ExchangeRateResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateService {
    @GET("/latest")
    fun getExchangeRate(@Query("base") base: String = "EUR"): Single<Response<ExchangeRateResponse>>
}