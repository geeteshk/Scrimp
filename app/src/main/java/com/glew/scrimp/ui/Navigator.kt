package com.glew.scrimp.ui

import com.glew.scrimp.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface Navigator {

    val destinationScreen: SharedFlow<Screen>
    suspend fun navigateTo(screen: Screen)
}

class NavigatorImpl @Inject constructor() : Navigator {

    private val _destinationScreen = MutableSharedFlow<Screen>(extraBufferCapacity = 1)
    override val destinationScreen: SharedFlow<Screen> = _destinationScreen.asSharedFlow()

    override suspend fun navigateTo(screen: Screen) {
        _destinationScreen.emit(screen)
    }
}