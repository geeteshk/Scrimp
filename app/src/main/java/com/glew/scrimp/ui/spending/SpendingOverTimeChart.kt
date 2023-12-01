package com.glew.scrimp.ui.spending

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.glew.scrimp.extensions.axisLabelFormat
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.dashedShape
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.extension.ceil
import java.text.NumberFormat
import java.time.LocalDate
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun SpendingOverTimeChart(
    budget: Float,
    numberFormat: NumberFormat,
    totalDailySpending: ChartEntryModelProducer,
    startDate: LocalDate
) {
    Chart(
        chart = lineChart(
            decorations = buildList {
                if (totalDailySpending.getModel().maxY > budget) {
                    add(rememberThresholdLine(budget, numberFormat))
                }
            },
            axisValuesOverrider = AxisValuesOverrider.fixed(
                maxY = (max(
                    budget,
                    totalDailySpending.getModel().maxY
                ) / 100).ceil * 100
            ),
            lines = currentChartStyle.lineChart.lines.map {
                it.copy(
                    lineColor = MaterialTheme.colorScheme.primary.toArgb(),
                    lineBackgroundShader = DynamicShaders.fromBrush(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                MaterialTheme.colorScheme.primary.copy(alpha = DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                            ),
                        ),
                    ),
                )
            },
            spacing = 48.dp,
        ),
        chartModelProducer = totalDailySpending,
        startAxis = startAxis(
            valueFormatter = { x, _ ->
                numberFormat.format(x) ?: ""
            }
        ),
        bottomAxis = bottomAxis(
            valueFormatter = { x, _ ->
                if (x.roundToInt() == 0) "" else startDate.plusDays(x.toLong())?.axisLabelFormat() ?: ""
            }
        ),
        horizontalLayout = HorizontalLayout.FullWidth(
            endPaddingDp = 0F,
            startPaddingDp = 16F
        ),
        getXStep = { 1F },
        runInitialAnimation = true
    )
}

@Composable
private fun rememberThresholdLine(
    budget: Float,
    numberFormat: NumberFormat
): ThresholdLine {
    val label = textComponent(
        color = MaterialTheme.colorScheme.onErrorContainer,
        background = shapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.errorContainer),
        padding = thresholdLineLabelPadding,
        margins = thresholdLineLabelMargins,
        typeface = Typeface.MONOSPACE,
    )
    val line = shapeComponent(
        color = MaterialTheme.colorScheme.error,
        shape = Shapes.dashedShape(
            shape = Shapes.pillShape,
            dashLength = 2.dp,
            gapLength = 2.dp,
        )
    )
    return remember(label, line) {
        ThresholdLine(
            thresholdValue = budget,
            thresholdLabel = numberFormat.format(budget),
            labelComponent = label,
            lineComponent = line
        )
    }
}

private val thresholdLineLabelHorizontalPaddingValue = 8.dp
private val thresholdLineLabelVerticalPaddingValue = 2.dp
private val thresholdLineLabelMarginValue = 4.dp
private val thresholdLineLabelPadding =
    dimensionsOf(thresholdLineLabelHorizontalPaddingValue, thresholdLineLabelVerticalPaddingValue)
private val thresholdLineLabelMargins = dimensionsOf(thresholdLineLabelMarginValue)