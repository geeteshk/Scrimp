package com.glew.scrimp.data

import java.time.LocalDate

data class Trip(
    val id: Int = 0,
    val name: String,
    val countryCode: String,
    val date: LocalDate,
    val budget: Double,
    val isArchived: Boolean,
    val isFavorite: Boolean,
    val expenses: List<Expense> = emptyList(),
)