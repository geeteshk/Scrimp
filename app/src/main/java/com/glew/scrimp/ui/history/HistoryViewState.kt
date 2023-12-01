package com.glew.scrimp.ui.history

import com.glew.scrimp.data.ExpenseCategory

data class HistoryViewState(
    val hasCurrentTrip: Boolean = false,
    val items: List<HistoryItem> = emptyList()
)

data class HistoryItem(
    val id: Int,
    val date: String,
    val title: String,
    val location: String,
    val countryFlag: String,
    val budget: String,
    val progresses: Map<ExpenseCategory, Float>,
    val expenseBreakdownMap: Map<ExpenseCategory, String>,
    val formattedZero: String,
    val spent: String,
    val variance: String,
    val isVariancePositive: Boolean,
    val isFavorite: Boolean,
)