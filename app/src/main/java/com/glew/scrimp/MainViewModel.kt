package com.glew.scrimp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glew.scrimp.cache.ExchangeRatesDao
import com.glew.scrimp.data.Trip
import com.glew.scrimp.data.TripRepository
import com.glew.scrimp.network.ExchangeRatesApi
import com.glew.scrimp.ui.MainViewState
import com.glew.scrimp.ui.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TripRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _viewState = mutableStateOf(MainViewState())
    val viewState: State<MainViewState> = _viewState

    private lateinit var tripFlow: StateFlow<Trip?>

    init {

        viewModelScope.launch {
            tripFlow = repository.getCurrentTrip().stateIn(this)
            tripFlow.map {
                MainViewState(
                    showSpendingTab = it != null
                )
            }.collect {
                _viewState.value = it
            }
        }
    }

    fun onNewExpenseClick() = viewModelScope.launch {
        val tripId = tripFlow.value?.id ?: return@launch
        navigator.navigateTo(Screen.Expense(tripId = tripId))
    }
}