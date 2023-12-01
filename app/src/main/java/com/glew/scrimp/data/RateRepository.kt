package com.glew.scrimp.data

import kotlinx.coroutines.flow.Flow

interface RateRepository {

    suspend fun fetchLatestRates()

    fun getRate(symbol: String): Flow<Double>
}