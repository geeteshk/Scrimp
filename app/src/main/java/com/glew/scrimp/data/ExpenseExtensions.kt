package com.glew.scrimp.data

import android.net.Uri
import com.glew.scrimp.cache.ExpenseEntity
import com.glew.scrimp.extensions.defaultFormat
import com.glew.scrimp.extensions.order
import com.patrykandpatrick.vico.core.entry.FloatEntry
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.math.max

val ExpenseEntity.model: Expense
    get() = Expense(
        id = this.id,
        title = this.title,
        amount = this.amount,
        instant = Instant.ofEpochSecond(this.epochSecond),
        notes = this.notes,
        category = this.category,
        receiptUri = this.receiptUri?.let { Uri.parse(it) }
    )

fun Expense.entity(tripId: Int) = ExpenseEntity(
    id = this.id,
    tripId = tripId,
    title = this.title,
    amount = this.amount,
    epochSecond = this.instant.epochSecond,
    notes = this.notes,
    category = this.category,
    receiptUri = this.receiptUri?.toString()
)

fun List<Expense>.toProgressMap(budget: Double): Map<ExpenseCategory, Float> {

    val divisor = max(budget, this.sumOf { it.amount })

    return this
        .groupBy(
            keySelector = { expense -> expense.category },
            valueTransform = { expense -> expense.amount }
        )
        .toSortedMap(compareBy { category -> category.order })
        .mapValues { entry -> (entry.value.sum() / divisor).toFloat() }
}

fun List<Expense>.toBreakdownMap(format: NumberFormat): Map<ExpenseCategory, String> {
    return this
        .groupBy(
            keySelector = { expense -> expense.category },
            valueTransform = { expense -> expense.amount }
        )
        .toSortedMap(compareBy { category -> category.order })
        .mapValues { entry -> format.format(entry.value.sum()) }
}

fun List<Expense>.toSpendingOverTimeValues(startDate: LocalDate): List<FloatEntry> {
    val values = mutableListOf<FloatEntry>()
    var runningSum = 0F

    values.add(FloatEntry(0F, runningSum))

    this.sortedBy { it.instant }
        .forEach {
            val localDateTime = it.instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            val hourOfDay = localDateTime.hour
            val minuteOfHour = localDateTime.minute

            val daysElapsed = ChronoUnit.DAYS.between(startDate, localDateTime)
            val minutesElapsed = (60 * hourOfDay) + minuteOfHour
            val percentagePassed = daysElapsed + (minutesElapsed / 1440F)
            runningSum += it.amount.toFloat()
            values.add(FloatEntry(percentagePassed, runningSum))
        }

    return values
}

fun List<Expense>.toDailyStackedList(startDate: LocalDate): List<List<FloatEntry>> {
    val stacked = mutableListOf<List<FloatEntry>>()

    val stackMap = this.sortedBy { it.instant }
        .groupBy {
            it.category
        }
        .mapValues {
            it.value
                .groupBy { expense ->
                    val localDate = expense.instant.atZone(ZoneId.systemDefault()).toLocalDate()
                    ChronoUnit.DAYS.between(startDate, localDate)
                }
                .mapValues { entry ->
                    entry.value.sumOf { exp -> exp.amount }.toFloat()
                }
        }
        .mapValues {
            it.value.map { entry ->
                FloatEntry(entry.key.toFloat(), entry.value)
            }
        }

    ExpenseCategory.sortedValues().forEach {
        val sums = stackMap[it] ?: emptyList()
        stacked.add(sums)
    }

    return stacked
}