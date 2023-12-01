package com.glew.scrimp.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun addExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpense(id: Int): Flow<ExpenseEntity>
}