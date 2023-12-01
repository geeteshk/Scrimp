package com.glew.scrimp.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val countryCode: String,
    val epochDay: Long,
    val budget: Double,
    val isArchived: Boolean,
    val isFavorite: Boolean,
)
