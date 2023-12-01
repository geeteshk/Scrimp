package com.glew.scrimp.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.glew.scrimp.data.ExpenseCategory

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["tripId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tripId: Int,
    val title: String,
    val amount: Double,
    val epochSecond: Long,
    val notes: String,
    val category: ExpenseCategory,
    val receiptUri: String?,
)
