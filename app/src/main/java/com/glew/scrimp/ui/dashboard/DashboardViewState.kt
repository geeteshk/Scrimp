package com.glew.scrimp.ui.dashboard

import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.ui.summary.ExpenseItem
import java.time.LocalTime

data class DashboardViewState(
    val card: DashboardCard? = null,
    val isBrowsePreviousTripsVisible: Boolean = false,
)

data class DashboardCard(
    val name: String,
    val location: String,
    val countryCode: String,
    val budget: String,
    val spent: String,
    val remaining: String,
    val progresses: Map<ExpenseCategory, Float>,
    val expenseBreakdownMap: Map<ExpenseCategory, String>,
    val recentExpenses: List<DashboardExpenseItem>,
    val insights: DashboardInsights?,
)

data class DashboardExpenseItem(
    val id: Int,
    val title: String,
    val amount: String,
    val time: LocalTime,
    val category: ExpenseCategory
)

data class DashboardInsights(
    val todaySpent: String,
    val highestCategory: ExpenseCategory
)