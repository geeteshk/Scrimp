package com.glew.scrimp.data

import com.glew.scrimp.cache.TripEntity
import com.glew.scrimp.cache.TripWithExpenses
import java.time.Instant
import java.time.LocalDate

val TripEntity.model: Trip
    get() = Trip(
        id = this.id,
        name = this.name,
        countryCode = this.countryCode,
        date = LocalDate.ofEpochDay(this.epochDay),
        budget = this.budget,
        isArchived = this.isArchived,
        isFavorite = this.isFavorite,
    )

val TripWithExpenses.model: Trip
    get() = Trip(
        id = this.trip.id,
        name = this.trip.name,
        countryCode = this.trip.countryCode,
        date = LocalDate.ofEpochDay(this.trip.epochDay),
        budget = this.trip.budget,
        isArchived = this.trip.isArchived,
        isFavorite = this.trip.isFavorite,
        expenses = this.expenses
            .sortedByDescending { it.epochSecond }
            .map { it.model },
    )

val Trip.entity: TripEntity
    get() = TripEntity(
        id = this.id,
        name = this.name,
        countryCode = this.countryCode,
        budget = this.budget,
        epochDay = this.date.toEpochDay(),
        isArchived = this.isArchived,
        isFavorite = this.isFavorite,
    )