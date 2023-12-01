package com.glew.scrimp.ui.spending

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material.icons.rounded.PieChartOutline
import androidx.compose.material.icons.rounded.StackedBarChart
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

sealed class InsightCardType {

    fun getIcon(): ImageVector {
        return when (this) {
            AvoidUnplannedExpenses -> Icons.Rounded.WarningAmber
            is DailySpendingAverage -> Icons.Default.StackedBarChart
            ExpenseEntryConsistency -> Icons.Rounded.EditCalendar
            is LargestSpendingCategory -> Icons.Rounded.PieChartOutline
        }
    }

    fun getTitle(): String {
        return when (this) {
            AvoidUnplannedExpenses -> "Unplanned Expenses"
            is DailySpendingAverage -> "Daily Average"
            ExpenseEntryConsistency -> "Expense Entry"
            is LargestSpendingCategory -> "Category Distribution"
        }
    }

    fun getBody(): AnnotatedString {
        return when (this) {
            is DailySpendingAverage -> buildAnnotatedString {
                append("Your daily spending average during this trip is ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(averageAmount)
                }
                append(".")
            }
            ExpenseEntryConsistency -> AnnotatedString("Consistently logging your expenses helps you stay on top of your budget.")
            is LargestSpendingCategory -> buildAnnotatedString {
                append("You've spent the most on ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(categoryName)
                }
                append(", which accounts for ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$spendingPercentage%")
                }
                append(" of your total expenses.")
            }
            AvoidUnplannedExpenses -> AnnotatedString("Planning ahead can help you avoid unexpected expenses.")
        }
    }

    data class DailySpendingAverage(val averageAmount: String) : InsightCardType()
    data class LargestSpendingCategory(val categoryName: String, val spendingPercentage: Int) : InsightCardType()
    object ExpenseEntryConsistency : InsightCardType()
    object AvoidUnplannedExpenses : InsightCardType()
}
