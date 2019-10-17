package com.jkirtyan.exchangerate.entity

data class ExchangeRateResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)