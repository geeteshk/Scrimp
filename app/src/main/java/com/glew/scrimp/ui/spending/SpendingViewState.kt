package com.glew.scrimp.ui.spending

import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import java.text.NumberFormat
import java.time.LocalDate

data class SpendingViewState(
    val budget: Float,
    val numberFormat: NumberFormat,
    val startDate: LocalDate,
    val totalDailySpending: ChartEntryModelProducer,
    val stackedDailySpending: ChartEntryModelProducer,
    val insightCards: List<InsightCardType>,
)