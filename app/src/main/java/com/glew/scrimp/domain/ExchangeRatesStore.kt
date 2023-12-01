package com.glew.scrimp.domain

import com.glew.scrimp.cache.ExchangeRatesDao
import com.glew.scrimp.data.ExchangeRates
import com.glew.scrimp.data.ExchangeRatesRepository
import com.glew.scrimp.extensions.entity
import com.glew.scrimp.extensions.model
import com.glew.scrimp.network.ExchangeRatesApi
import com.glew.scrimp.network.LatestExchangeRatesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class ExchangeRatesStore @Inject constructor(
    private val api: ExchangeRatesApi,
    private val dao: ExchangeRatesDao
) : ExchangeRatesRepository {

    override suspend fun load() {
        val lastUpdatedDate = dao.getRates()?.model?.date ?: LocalDate.EPOCH
        val dayDifference = ChronoUnit.DAYS.between(lastUpdatedDate, LocalDate.now())

        if (dayDifference >= 1) {
            val dto = tryFetchExchangeRates() ?: return
            dao.updateRates(dto.entity)
        }
    }

    override fun get(): Flow<ExchangeRates?> {
        return dao.getRatesAsFlow().map { it?.model }
    }

    private suspend fun tryFetchExchangeRates(): LatestExchangeRatesResponse? {
        return api.getLatestMinifiedRates().body()
            ?: api.getLatestRates().body()
            ?: api.getLatestFallbackRates(FALLBACK_MINIFIED_URL).body()
            ?: api.getLatestFallbackRates(FALLBACK_URL).body()
    }

    companion object {
        private const val FALLBACK_MINIFIED_URL = "https://raw.githubusercontent.com/fawazahmed0/currency-api/1/latest/currencies/usd.min.json"
        private const val FALLBACK_URL = "https://raw.githubusercontent.com/fawazahmed0/currency-api/1/latest/currencies/usd.json"
    }
}