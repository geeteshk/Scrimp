package com.glew.scrimp.ui.spending

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.data.Trip
import com.glew.scrimp.data.TripRepository
import com.glew.scrimp.data.toDailyStackedList
import com.glew.scrimp.data.toSpendingOverTimeValues
import com.glew.scrimp.extensions.order
import com.glew.scrimp.extensions.title
import com.glew.scrimp.ui.dashboard.DashboardViewModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SpendingViewModel @Inject constructor(
    private val repository: TripRepository
) : ViewModel() {

    private val _viewState = mutableStateOf<SpendingViewState?>(null)
    val viewState: State<SpendingViewState?> = _viewState

    private lateinit var data: StateFlow<Trip?>

    init {
        viewModelScope.launch {
            data = repository.getCurrentTrip().stateIn(this)
            data.map { trip ->

                trip ?: return@map null

                val startDate = trip.date
                val values = trip.expenses.toSpendingOverTimeValues(startDate)
                val stacked = trip.expenses.toDailyStackedList(startDate)

                val locale = Locale("en", trip.countryCode)
                val formatter = NumberFormat.getCurrencyInstance(locale)

                val currentViewState = _viewState.value

                val totalDailySpendingProducer = currentViewState?.totalDailySpending ?: ChartEntryModelProducer()
                totalDailySpendingProducer.setEntries(values)

                val stackedDailySpendingProducer = currentViewState?.stackedDailySpending ?: ChartEntryModelProducer()
                stackedDailySpendingProducer.setEntries(*stacked.toTypedArray())

                val insightCards = buildList {

                    val maxDay = trip.expenses.maxBy { it.instant }
                    val maxDate = maxDay.instant.atZone(ZoneId.systemDefault())?.toLocalDate() ?: startDate
                    val totalDays = ChronoUnit.DAYS.between(startDate, maxDate)
                    val dailyAverage = trip.expenses.sumOf { it.amount }.div(totalDays)

                    if (dailyAverage > 0) {
                        add(InsightCardType.DailySpendingAverage(formatter.format(dailyAverage)))
                    }

                    val totalSpend = trip.expenses.sumOf { it.amount }
                    val largestCategory = trip.expenses.groupBy { it.category }.mapValues { it.value.sumOf { it.amount } }.maxBy { it.value }

                    if (totalSpend > 0) {
                        add(
                            InsightCardType.LargestSpendingCategory(
                                categoryName = largestCategory.key.title,
                                spendingPercentage = ((largestCategory.value / totalSpend) * 100).toInt()
                            )
                        )
                    }

                    add(InsightCardType.ExpenseEntryConsistency)
                    add(InsightCardType.AvoidUnplannedExpenses)
                }

                SpendingViewState(
                    budget = trip.budget.toFloat(),
                    numberFormat = formatter,
                    startDate = startDate,
                    totalDailySpending = totalDailySpendingProducer,
                    stackedDailySpending = stackedDailySpendingProducer,
                    insightCards = insightCards,
                )
            }.collect {
                _viewState.value = it
            }
        }
    }
}