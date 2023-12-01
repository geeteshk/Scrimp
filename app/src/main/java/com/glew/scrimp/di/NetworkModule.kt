package com.glew.scrimp.di

import com.glew.scrimp.network.ExchangeRatesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Named("baseUrl")
    @Provides
    fun provideBaseUrl() = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"

    @Provides
    @Singleton
    fun provideRetrofit(@Named("baseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesApi {
        return retrofit.create(ExchangeRatesApi::class.java)
    }
}