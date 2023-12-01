package com.glew.scrimp.domain

import android.util.Log
import com.glew.scrimp.cache.TripDao
import com.glew.scrimp.cache.TripEntity
import com.glew.scrimp.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TripStore @Inject constructor(
    private val tripDao: TripDao
) : TripRepository {

    override suspend fun getCurrentTrip(): Flow<Trip?> {
        return tripDao.getCurrentTripWithExpenses().map { it?.model }
    }

    override suspend fun getTripWithExpenses(id: Int): Flow<Trip> {
        return tripDao.getTripWithExpenses(id).map { it.model }
    }

    override suspend fun hasArchivedTrips(): Flow<Boolean> {
        return tripDao.getArchivedTrips().map { it.isNotEmpty() }
    }

    override suspend fun createNewTrip(trip: Trip): Int {
        val entity = trip.entity
        return tripDao.addTrip(entity).toInt()
    }

    override suspend fun updateTrip(trip: Trip): Int {
        val entity = trip.entity
        tripDao.updateTrip(entity)
        return entity.id
    }

    override suspend fun archiveTrip(id: Int) {
        tripDao.archiveTrip(id)
    }

    override suspend fun getArchivedTrips(): Flow<List<Trip>> {
        return tripDao.getArchivedTrips().map { it.map { entity -> entity.model } }
    }

    override suspend fun hasCurrentTrip(): Flow<Boolean> {
        return getCurrentTrip().map { it != null }
    }

    override suspend fun deleteTrip(id: Int) {
        tripDao.deleteTrip(id)
    }

    override suspend fun deleteExpense(id: Int) {
        tripDao.deleteExpense(id)
    }

    override suspend fun updateTripFavorite(id: Int, favorite: Boolean) {
        tripDao.updateTripFavorite(id, favorite)
    }
}