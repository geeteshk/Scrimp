package com.glew.scrimp.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Query("SELECT * FROM trips WHERE isArchived = 0")
    fun getCurrentTripWithExpenses(): Flow<TripWithExpenses?>

    @Query("SELECT * FROM trips WHERE id = :id")
    fun getTripWithExpenses(id: Int): Flow<TripWithExpenses>

    @Insert
    suspend fun addTrip(tripEntity: TripEntity): Long

    @Update
    suspend fun updateTrip(tripEntity: TripEntity)

    @Query("UPDATE trips SET isArchived = 1 WHERE id = :id")
    suspend fun archiveTrip(id: Int)

    @Query("SELECT * FROM trips WHERE isArchived = 1 ORDER BY isFavorite DESC, epochDay DESC")
    fun getArchivedTrips(): Flow<List<TripWithExpenses>>

    @Query("DELETE FROM trips WHERE id = :id")
    suspend fun deleteTrip(id: Int)

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteExpense(id: Int)

    @Query("UPDATE trips SET isFavorite = :favorite WHERE id = :id")
    suspend fun updateTripFavorite(id: Int, favorite: Boolean)
}