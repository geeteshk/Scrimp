package com.glew.scrimp.ui.spending

import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.axisLabelFormat
import com.glew.scrimp.extensions.getHarmonizedColor
import com.glew.scrimp.extensions.title
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.horizontalLegend
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.extension.ceil
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun DailySpendingChart(
    stackedDailySpending: ChartEntryModelProducer,
    numberFormat: NumberFormat,
    startDate: LocalDate
) {
    Chart(
        chart = columnChart(
            mergeMode = ColumnChart.MergeMode.Stack,
            columns = ExpenseCategory.sortedValues()
                .map { it.getHarmonizedColor() }
                .map {
                    LineComponent(
                        color = it.toArgb(),
                        thicknessDp = 8F,
                        shape = Shapes.rectShape
                    )
                },
            spacing = 52.dp,
            axisValuesOverrider = AxisValuesOverrider.fixed(maxY = (stackedDailySpending.getModel().stackedPositiveY / 100).ceil * 100)
        ),
        chartModelProducer = stackedDailySpending,
        startAxis = startAxis(
            valueFormatter = { x, _ ->
                numberFormat.format(x) ?: ""
            }
        ),
        bottomAxis = bottomAxis(
            valueFormatter = { x, _ ->
                startDate.plusDays(x.toLong())?.axisLabelFormat() ?: ""
            },
            guideline = null
        ),
        legend = rememberLegend(),
        runInitialAnimation = true
    )
}

@Composable
private fun rememberLegend() = horizontalLegend(
    items = ExpenseCategory.sortedValues().map { category ->
        legendItem(
            icon = shapeComponent(Shapes.rectShape, category.getHarmonizedColor()),
            label = textComponent(
                color = currentChartStyle.axis.axisLabelColor,
                textSize = 10.sp,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = category.title,
        )
    },
    iconSize = 8.dp,
    iconPadding = 8.dp,
    spacing = 16.dp,
    padding = dimensionsOf(top = 8.dp),
)