package com.glew.scrimp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.glew.scrimp.cache.AppDatabase
import com.glew.scrimp.cache.ExchangeRatesDao
import com.glew.scrimp.cache.ExpenseDao
import com.glew.scrimp.cache.TripDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "scrimp"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTripDao(appDatabase: AppDatabase): TripDao {
        return appDatabase.tripDao()
    }

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.expenseDao()
    }

    @Provides
    fun provideExchangeRatesDao(appDatabase: AppDatabase): ExchangeRatesDao {
        return appDatabase.exchangeRatesDao()
    }
}