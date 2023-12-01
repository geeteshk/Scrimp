package com.glew.scrimp.data

import android.net.Uri
import com.glew.scrimp.extensions.order
import java.time.Instant

data class Expense(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val instant: Instant,
    val notes: String,
    val category: ExpenseCategory,
    val receiptUri: Uri?,
)

enum class ExpenseCategory {

    ACCOMMODATION, TRANSPORTATION, FOOD, ENTERTAINMENT, SHOPPING, OTHER;

    companion object {
        fun sortedValues() = values().sortedBy { it.order }
    }
}