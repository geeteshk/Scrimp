package com.glew.scrimp.data

import kotlinx.coroutines.flow.Flow

interface ExchangeRatesRepository {
    suspend fun load()
    fun get(): Flow<ExchangeRates?>
}