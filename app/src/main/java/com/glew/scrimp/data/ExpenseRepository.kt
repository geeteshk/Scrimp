package com.glew.scrimp.data

import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    suspend fun createExpense(expense: Expense, tripId: Int)

    suspend fun updateExpense(expense: Expense, tripId: Int)

    fun getExpense(id: Int): Flow<Expense>
}