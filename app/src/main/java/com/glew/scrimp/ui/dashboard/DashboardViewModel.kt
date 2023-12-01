package com.glew.scrimp.ui.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glew.scrimp.Screen
import com.glew.scrimp.data.Trip
import com.glew.scrimp.data.TripRepository
import com.glew.scrimp.ui.Navigator
import com.glew.scrimp.countryFlag
import com.glew.scrimp.data.toBreakdownMap
import com.glew.scrimp.data.toProgressMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: TripRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _viewState = mutableStateOf(DashboardViewState())
    val viewState: State<DashboardViewState> = _viewState

    private lateinit var data: StateFlow<DashboardData>

    init {
        viewModelScope.launch {
            data = combine(
                repository.getCurrentTrip(),
                repository.hasArchivedTrips()
            ) { trip, hasArchivedTrips ->
                DashboardData(
                    trip = trip,
                    hasArchivedTrips = hasArchivedTrips
                )
            }.stateIn(this)

            data.map {
                DashboardViewState(
                    card = it.trip?.let { trip ->

                        val locale = Locale("en", trip.countryCode)
                        val formatter = NumberFormat.getCurrencyInstance(locale)

                        val budget = trip.budget
                        val spent = trip.expenses.sumOf { expense -> expense.amount }
                        val remaining = budget - spent

                        val progresses = trip.expenses.toProgressMap(trip.budget)
                        val breakdownMap = trip.expenses.toBreakdownMap(formatter)

                        val recentExpenses = trip.expenses
                            .sortedByDescending { expense -> expense.instant }
                            .take(3)
                            .map { expense ->
                                val zonedDateTime = expense.instant
                                    .atZone(ZoneId.systemDefault())

                                DashboardExpenseItem(
                                    id = expense.id,
                                    title = expense.title,
                                    amount = formatter.format(expense.amount),
                                    time = zonedDateTime.toLocalTime(),
                                    category = expense.category
                                )
                            }

                        val todayExpenses = trip.expenses.filter { expense ->
                            val zonedDateTime = expense.instant
                                .atZone(ZoneId.systemDefault())
                            val now = ZonedDateTime.now()
                            zonedDateTime.dayOfYear == now.dayOfYear && zonedDateTime.year == now.year
                        }

                        val insights = if (todayExpenses.isEmpty()) null else {
                            val todaySpentAmount = todayExpenses.sumOf { expense -> expense.amount }
                            val highestCategory = todayExpenses.groupBy(
                                keySelector = { expense -> expense.category },
                                valueTransform = { expense -> expense.amount }
                            ).maxBy { entry -> entry.value.sum() }.key

                            DashboardInsights(
                                todaySpent = formatter.format(todaySpentAmount),
                                highestCategory = highestCategory
                            )
                        }

                        DashboardCard(
                            name = trip.name,
                            location = locale.displayCountry,
                            countryCode = trip.countryCode,
                            budget = formatter.format(budget),
                            spent = formatter.format(spent),
                            remaining = formatter.format(remaining),
                            progresses = progresses,
                            expenseBreakdownMap = breakdownMap,
                            recentExpenses = recentExpenses,
                            insights = insights
                        )
                    },
                    isBrowsePreviousTripsVisible = it.hasArchivedTrips
                )
            }.collect {
                _viewState.value = it
            }
        }
    }

    fun onCreateTripClick() = viewModelScope.launch {
        navigator.navigateTo(Screen.Edit())
    }

    fun onMoreClick() = viewModelScope.launch {
        val tripId = data.value.trip?.id ?: return@launch
        navigator.navigateTo(Screen.ExpensesList(tripId))
    }

    fun onBrowsePreviousTripsClick() = viewModelScope.launch {
        navigator.navigateTo(Screen.BottomNavScreen.PastTrips)
    }

    fun onArchiveClick() = viewModelScope.launch {
        val tripId = data.value.trip?.id ?: return@launch
        repository.archiveTrip(tripId)
    }

    fun onExpenseItemClick(id: Int) = viewModelScope.launch {
        val tripId = data.value.trip?.id ?: return@launch
        navigator.navigateTo(Screen.ExpensesList(tripId))
        navigator.navigateTo(
            Screen.Expense(
                tripId = tripId,
                expenseId = id
            )
        )
    }

    fun onEditClick() = viewModelScope.launch {
        val tripId = data.value.trip?.id ?: return@launch
        navigator.navigateTo(Screen.ExpensesList(tripId))
        navigator.navigateTo(Screen.Edit(tripId))
    }

    fun onAddExpenseClick() = viewModelScope.launch {
        val tripId = data.value.trip?.id ?: return@launch
        navigator.navigateTo(Screen.ExpensesList(tripId))
        navigator.navigateTo(Screen.Expense(tripId))

    }

    data class DashboardData(
        val trip: Trip?,
        val hasArchivedTrips: Boolean
    )
}