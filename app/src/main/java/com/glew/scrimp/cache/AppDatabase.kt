package com.glew.scrimp.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TripEntity::class, ExpenseEntity::class, ExchangeRatesEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun exchangeRatesDao(): ExchangeRatesDao
}