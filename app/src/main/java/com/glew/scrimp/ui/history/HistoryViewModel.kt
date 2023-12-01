package com.glew.scrimp.ui.history

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glew.scrimp.Screen
import com.glew.scrimp.data.Trip
import com.glew.scrimp.data.TripRepository
import com.glew.scrimp.extensions.defaultFormat
import com.glew.scrimp.ui.Navigator
import com.glew.scrimp.countryFlag
import com.glew.scrimp.data.toBreakdownMap
import com.glew.scrimp.data.toProgressMap
import com.glew.scrimp.data.toSpendingOverTimeValues
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TripRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _viewState = mutableStateOf(HistoryViewState())
    val viewState: State<HistoryViewState> = _viewState

    private lateinit var data: StateFlow<HistoryData>

    init {
        viewModelScope.launch {
            data = combine(
                repository.getArchivedTrips(),
                repository.hasCurrentTrip()
            ) { trips, hasCurrentTrip ->
                HistoryData(
                    trips = trips,
                    hasCurrentTrip = hasCurrentTrip
                )
            }.stateIn(this)

            data.map {
                HistoryViewState(
                    hasCurrentTrip = it.hasCurrentTrip,
                    items = it.trips.map { trip ->

                        val locale = Locale("en", trip.countryCode)
                        val formatter = NumberFormat.getCurrencyInstance(locale)

                        val progresses = trip.expenses.toProgressMap(trip.budget)
                        val breakdownMap = trip.expenses.toBreakdownMap(formatter)
                        val expensesSum = trip.expenses.sumOf { expense -> expense.amount }
                        val variance = trip.budget - expensesSum

                        HistoryItem(
                            id = trip.id,
                            date = trip.date.defaultFormat(),
                            title = trip.name,
                            location = locale.displayCountry,
                            countryFlag = countryFlag(trip.countryCode),
                            budget = formatter.format(trip.budget),
                            progresses = progresses,
                            expenseBreakdownMap = breakdownMap,
                            formattedZero = formatter.format(0),
                            spent = formatter.format(expensesSum),
                            variance = formatter.format(abs(variance)),
                            isVariancePositive = variance > 0,
                            isFavorite = trip.isFavorite,
                        )
                    }
                )
            }.collect {
                _viewState.value = it
            }
        }
    }

    fun onViewCurrentTripClick() = viewModelScope.launch {
        navigator.navigateTo(Screen.BottomNavScreen.Dashboard)
    }

    fun onCreateTripClick() = viewModelScope.launch {
        navigator.navigateTo(Screen.BottomNavScreen.Dashboard)
        navigator.navigateTo(Screen.Edit())
    }

    fun onItemClick(id: Int) = viewModelScope.launch {
        navigator.navigateTo(Screen.ExpensesList(tripId = id))
    }

    fun onDeleteTripClick(id: Int) = viewModelScope.launch {
        repository.deleteTrip(id)
    }

    fun onFavoriteClick(id: Int, favorite: Boolean) = viewModelScope.launch {
        repository.updateTripFavorite(id, favorite)
    }

    data class HistoryData(
        val trips: List<Trip>,
        val hasCurrentTrip: Boolean
    )
}