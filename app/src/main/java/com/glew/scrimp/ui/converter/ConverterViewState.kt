package com.glew.scrimp.ui.converter

import com.neovisionaries.i18n.CurrencyCode

data class ConverterViewState(
    val bigNumber: ULong = 0u,
    val smallNumber: ULong = 0u,
    val isBigNumberSelected: Boolean = true,
    val selectedCurrency: CurrencyCode = CurrencyCode.USD,
    val currencies: List<CurrencyCode> = emptyList(),
    val rateMap: Map<String, Double> = emptyMap()
) {

    fun getEnteredAmount(): Double {
        return bigNumber.toDouble() + (smallNumber.toDouble() / 100.0)
    }
}