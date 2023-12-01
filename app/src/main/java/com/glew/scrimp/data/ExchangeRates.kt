package com.glew.scrimp.data

import java.time.LocalDate

data class ExchangeRates(
    val date: LocalDate,
    val rates: Map<String, Double>
)
