package com.glew.scrimp.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRatesDao {

    @Query("SELECT * FROM exchange_rates")
    suspend fun getRates(): ExchangeRatesEntity?

    @Query("SELECT * FROM exchange_rates")
    fun getRatesAsFlow(): Flow<ExchangeRatesEntity?>

    @Insert
    suspend fun addRates(entity: ExchangeRatesEntity)

    @Query("DELETE FROM exchange_rates")
    suspend fun deleteAllRates()

    @Transaction
    suspend fun updateRates(entity: ExchangeRatesEntity) {
        deleteAllRates()
        addRates(entity)
    }
}