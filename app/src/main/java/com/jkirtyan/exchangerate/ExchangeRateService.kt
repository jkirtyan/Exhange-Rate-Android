package com.jkirtyan.exchangerate

import com.jkirtyan.exchangerate.entity.ExchangeRateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateService {
    @GET("/latest")
    fun getExchangeRate(@Query("base") base: String = "EUR"): Call<ExchangeRateResponse>
}