package com.glew.scrimp.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatestExchangeRatesResponse(
    val date: String,
    val usd: Map<String, Double>
)