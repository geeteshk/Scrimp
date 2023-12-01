package com.glew.scrimp.ui.summary

import android.net.Uri
import com.glew.scrimp.data.ExpenseCategory
import java.time.LocalDate
import java.time.LocalTime

data class ExpensesListViewState(
    val name: String,
    val location: String,
    val date: String,
    val budget: String,
    val expenseItems: Map<Long, List<ExpenseItem>>,
    val isArchived: Boolean,
    val selectedFilters: Set<ExpenseCategory> = emptySet(),
    val selectedPriceRange: ClosedFloatingPointRange<Float>,
    val minMaxPriceRange: ClosedFloatingPointRange<Float>,
    val priceRangeText: String,
    val minMaxDateRange: ClosedRange<LocalDate>,
    val selectedDateRange: ClosedRange<LocalDate>,
    val dateRangeText: String,
)

data class ExpenseItem(
    val id: Int,
    val title: String,
    val amount: String,
    val numberAmount: Double,
    val time: LocalTime,
    val category: ExpenseCategory,
    val receiptUri: Uri?,
    val notes: String,
)

enum class FilterSelectionItem {
    CATEGORY, PRICE, DATE
}