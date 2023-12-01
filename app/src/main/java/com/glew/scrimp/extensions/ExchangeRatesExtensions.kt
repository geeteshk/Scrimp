package com.glew.scrimp.extensions

import androidx.compose.ui.text.toLowerCase
import com.glew.scrimp.cache.ExchangeRatesEntity
import com.glew.scrimp.data.ExchangeRates
import com.glew.scrimp.network.LatestExchangeRatesResponse
import com.neovisionaries.i18n.CurrencyCode
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.time.LocalDate
import java.util.Currency
import java.util.Locale

private val moshiMapAdapter = Moshi.Builder()
    .build()
    .adapter<Map<String, Double>>(
        Types.newParameterizedType(
            Map::class.java, String::class.java, Double::class.javaObjectType
        )
    )

private val supportedCurrencies = CurrencyCode.values().filter { it.countryList.isNotEmpty() }.map { it.name.lowercase() }

val LatestExchangeRatesResponse.entity: ExchangeRatesEntity
    get() = ExchangeRatesEntity(
        date = this.date,
        rates = moshiMapAdapter.toJson(this.usd.plus("usd" to 1.0).filter { supportedCurrencies.contains(it.key) })
    )

val ExchangeRatesEntity.model: ExchangeRates
    get() = ExchangeRates(
        date = try { LocalDate.parse(this.date) } catch (e: Exception) { LocalDate.EPOCH },
        rates = moshiMapAdapter.fromJson(this.rates) ?: emptyMap()
    )