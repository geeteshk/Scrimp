package com.glew.scrimp.cache

import androidx.room.Embedded
import androidx.room.Relation

data class TripWithExpenses(
    @Embedded
    val trip: TripEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "tripId"
    )
    val expenses: List<ExpenseEntity>,
)
