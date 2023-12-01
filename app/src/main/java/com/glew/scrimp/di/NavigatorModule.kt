package com.glew.scrimp.di

import com.glew.scrimp.ui.Navigator
import com.glew.scrimp.ui.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NavigatorModule {

    @Binds
    @Singleton
    abstract fun provideNavigator(impl: NavigatorImpl): Navigator
}