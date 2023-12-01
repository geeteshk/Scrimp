package com.glew.scrimp.ui.summary

import android.net.Uri
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
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpensesListViewModel @Inject constructor(
    private val repository: TripRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val _viewState = mutableStateOf<ExpensesListViewState?>(null)
    val viewState: State<ExpensesListViewState?> = _viewState

    private var tripJob: Job? = null
    private lateinit var tripFlow: StateFlow<Trip>
    private lateinit var format: NumberFormat

    fun initializeUi(tripId: Int) {
        if (tripId != -1) {
            viewModelScope.launch {
                loadTrip(tripId)
            }
        }
    }

    private suspend fun loadTrip(id: Int) {
        tripJob?.cancel()
        tripJob = viewModelScope.launch {
            tripFlow = repository.getTripWithExpenses(id).stateIn(this)
            tripFlow.map {

                val locale = Locale("en", it.countryCode)
                format = NumberFormat.getCurrencyInstance(locale)

                val expenseItems = it.expenses.groupBy(
                    keySelector = { expense ->
                        val zonedDateTime = expense.instant
                            .atZone(ZoneId.systemDefault())

                        zonedDateTime.toLocalDate().toEpochDay()
                    },
                    valueTransform = { expense ->
                        val zonedDateTime = expense.instant
                            .atZone(ZoneId.systemDefault())

                        ExpenseItem(
                            id = expense.id,
                            title = expense.title,
                            amount = format.format(expense.amount),
                            numberAmount = expense.amount,
                            time = zonedDateTime.toLocalTime(),
                            category = expense.category,
                            receiptUri = expense.receiptUri,
                            notes = expense.notes,
                        )
                    }
                )

                val minPrice = it.expenses.minOfOrNull { expense -> expense.amount } ?: 0
                val maxPrice = it.expenses.maxOfOrNull { expense -> expense.amount } ?: 0
                val priceRange = minPrice.toFloat() .. maxPrice.toFloat()

                val nowInstant = Instant.now()
                val oldestInstant = it.expenses.minOfOrNull { expense -> expense.instant } ?: nowInstant
                val newestInstant = it.expenses.maxOfOrNull { expense -> expense.instant } ?: nowInstant
                val oldestLocalDate = oldestInstant.atZone(ZoneId.systemDefault()).toLocalDate()
                val newestLocalDate = newestInstant.atZone(ZoneId.systemDefault()).toLocalDate()
                val dateRange = oldestLocalDate .. newestLocalDate

                ExpensesListViewState(
                    name = it.name,
                    location = "${locale.displayCountry} ${countryFlag(it.countryCode)}",
                    date = it.date.defaultFormat(),
                    budget = format.format(it.budget),
                    expenseItems = expenseItems,
                    isArchived = it.isArchived,
                    selectedPriceRange = priceRange,
                    minMaxPriceRange = priceRange,
                    priceRangeText = getPriceRangeText(priceRange),
                    minMaxDateRange = dateRange,
                    selectedDateRange = dateRange,
                    dateRangeText = getDateRangeText(dateRange),
                )
            }.collect {
                _viewState.value = it
            }
        }
    }

    fun onBackPressed() = viewModelScope.launch {
        navigator.navigateTo(Screen.Back)
    }

    fun onExpenseItemClick(expenseId: Int) = viewModelScope.launch {
        val tripId = tripFlow.value.id
        navigator.navigateTo(
            Screen.Expense(
                tripId = tripId,
                expenseId = expenseId
            )
        )
    }

    fun onDeleteExpenseClick(expenseId: Int) = viewModelScope.launch {
        repository.deleteExpense(expenseId)
    }

    fun onLogExpenseClick() = viewModelScope.launch {
        val tripId = tripFlow.value.id
        navigator.navigateTo(
            Screen.Expense(tripId = tripId)
        )
    }

    fun onFilterSelected(category: ExpenseCategory) {
        _viewState.update {
            it?.copy(selectedFilters = it.selectedFilters + category)
        }
    }

    fun onFilterUnselected(category: ExpenseCategory) {
        _viewState.update {
            it?.copy(selectedFilters = it.selectedFilters - category)
        }
    }

    fun onRemoveFiltersClick() {
        _viewState.update {
            it?.copy(selectedFilters = emptySet())
        }
    }

    fun onPriceFilterValueChanged(newRange: ClosedFloatingPointRange<Float>) {
        _viewState.update {
            it?.copy(
                selectedPriceRange = newRange,
                priceRangeText = getPriceRangeText(newRange)
            )
        }
    }

    private fun getPriceRangeText(range: ClosedFloatingPointRange<Float>): String {
        return when (range.start) {
            range.endInclusive -> format.format(range.start.toDouble())
            else -> "${format.format(range.start.toDouble())} to ${format.format(range.endInclusive.toDouble())}"
        }
    }

    fun onRemovePriceFilterClick() {
        _viewState.update {
            it?.copy(selectedPriceRange = it.minMaxPriceRange)
        }
    }

    fun onDateRangeSelected(range: ClosedRange<LocalDate>) {
        _viewState.update {
            it?.copy(
                selectedDateRange = range,
                dateRangeText = getDateRangeText(range)
            )
        }
    }

    private fun getDateRangeText(range: ClosedRange<LocalDate>): String {
        return when (range.start) {
            range.endInclusive -> range.start.defaultFormat()
            else -> "${range.start.defaultFormat()} to ${range.endInclusive.defaultFormat()}"
        }
    }

    fun onRemoveDateFilterClick() {
        _viewState.update {
            it?.copy(selectedDateRange = it.minMaxDateRange)
        }
    }
}