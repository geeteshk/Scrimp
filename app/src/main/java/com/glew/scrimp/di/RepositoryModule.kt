package com.glew.scrimp.di

import com.glew.scrimp.data.ExchangeRatesRepository
import com.glew.scrimp.data.ExpenseRepository
import com.glew.scrimp.domain.TripStore
import com.glew.scrimp.data.TripRepository
import com.glew.scrimp.domain.ExchangeRatesStore
import com.glew.scrimp.domain.ExpenseStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideTripRepository(store: TripStore): TripRepository

    @Binds
    abstract fun provideExpenseRepository(store: ExpenseStore): ExpenseRepository

    @Binds
    abstract fun provideExchangeRatesRepository(store: ExchangeRatesStore): ExchangeRatesRepository
}