package com.glew.scrimp.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRatesEntity(
    @PrimaryKey val date: String,
    val rates: String
)
