package com.glew.scrimp.data

import kotlinx.coroutines.flow.Flow

interface TripRepository {

    suspend fun getCurrentTrip(): Flow<Trip?>

    suspend fun getTripWithExpenses(id: Int): Flow<Trip>

    suspend fun hasArchivedTrips(): Flow<Boolean>

    suspend fun createNewTrip(trip: Trip): Int

    suspend fun updateTrip(trip: Trip): Int

    suspend fun archiveTrip(id: Int)

    suspend fun getArchivedTrips(): Flow<List<Trip>>

    suspend fun hasCurrentTrip(): Flow<Boolean>

    suspend fun deleteTrip(id: Int)

    suspend fun deleteExpense(id: Int)

    suspend fun updateTripFavorite(id: Int, favorite: Boolean)
}