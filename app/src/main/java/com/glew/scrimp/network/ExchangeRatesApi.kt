package com.glew.scrimp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ExchangeRatesApi {

    @GET("latest/currencies/usd.min.json")
    suspend fun getLatestMinifiedRates(): Response<LatestExchangeRatesResponse>

    @GET("latest/currencies/usd.json")
    suspend fun getLatestRates(): Response<LatestExchangeRatesResponse>

    @GET
    suspend fun getLatestFallbackRates(@Url url: String): Response<LatestExchangeRatesResponse>
}