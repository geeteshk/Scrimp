package com.glew.scrimp.domain

import com.glew.scrimp.cache.ExpenseDao
import com.glew.scrimp.data.Expense
import com.glew.scrimp.data.ExpenseRepository
import com.glew.scrimp.data.entity
import com.glew.scrimp.data.model
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseStore @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun createExpense(expense: Expense, tripId: Int) {
        val entity = expense.entity(tripId)
        expenseDao.addExpense(entity)
    }

    override suspend fun updateExpense(expense: Expense, tripId: Int) {
        val entity = expense.entity(tripId)
        expenseDao.updateExpense(entity)
    }

    override fun getExpense(id: Int): Flow<Expense> {
        return expenseDao.getExpense(id).map { it.model }
    }
}