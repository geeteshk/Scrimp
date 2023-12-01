package com.glew.scrimp.ui.dashboard

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.getHarmonizedColor
import kotlin.math.atan2
import kotlin.math.sqrt

@Composable
fun ExpensesCircularProgressIndicator(
    progresses: Map<ExpenseCategory, Float>,
    modifier: Modifier = Modifier,
    selectedCategory: SelectedCategory = SelectedCategory.None,
    trackColor: Color = ProgressIndicatorDefaults.linearTrackColor,
    strokeWidth: Dp = 32.dp,//ProgressIndicatorDefaults.CircularStrokeWidth,
    onCategoryClick: (SelectedCategory) -> Unit,
) {
    var progress by remember { mutableStateOf(0F) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "Expenses Progress",
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing
        )
    )

    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }

    val selectedStroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.times(1.5F).toPx(), cap = StrokeCap.Butt)
    }

    val gapWidth = 0F
    val fillSize = (360F - (gapWidth * progresses.size))

    var radius by remember {
        mutableStateOf(0F)
    }

    val harmonizedColorMap = ExpenseCategory.sortedValues().associateWith { it.getHarmonizedColor() }

    Canvas(
        modifier
            .progressSemantics(progresses.values.sum())
            .pointerInput(radius, animatedProgress) {
                detectTapGestures {
                    if (animatedProgress == 1F) {
                        val x = it.x - radius
                        val y = radius - it.y

                        val pointRadius = sqrt((x * x) + (y * y))

                        if (pointRadius in (radius * 0.75F)..(radius * 1.1F)) {
                            val pointAngle = Math.toDegrees(atan2(y, x).toDouble())
                            val adjustedDegrees = 90F - pointAngle
                            val furtherAdjustedDegrees = if (adjustedDegrees <= 0) {
                                360F + adjustedDegrees
                            } else {
                                adjustedDegrees
                            }

                            val sectorProgress = furtherAdjustedDegrees / 360F
                            var progressSum = 0F

                            progresses.entries.forEach { (category, progress) ->
                                if (sectorProgress in progressSum..(progressSum + progress)) {
                                    onCategoryClick(SelectedCategory.Category(category))
                                    return@detectTapGestures
                                }

                                progressSum += progress
                            }

                            onCategoryClick(SelectedCategory.Remaining)
                        } else {
                            onCategoryClick(SelectedCategory.None)
                        }
                    }
                }
            }
    ) {
        radius = this.center.x
        var previousProgress = 0F

        progresses.mapValues { it.value * animatedProgress }.entries.forEachIndexed { index, (category, progress) ->
            // Start at 12 o'clock
            val startAngle = 270F + (index * gapWidth) + (fillSize * previousProgress)
            val sweep = progress * fillSize
            val expenseCategory = (selectedCategory as? SelectedCategory.Category)?.expenseCategory
            drawDeterminateCircularIndicator(startAngle, sweep, harmonizedColorMap[category] ?: trackColor, if (expenseCategory == category) selectedStroke else stroke)
            previousProgress += progress
        }

        if (previousProgress < 0.98F) {
            val endStartAngle = 270F + (5 * gapWidth) + (fillSize * previousProgress)
            drawDeterminateCircularIndicator(
                startAngle = endStartAngle,
                sweep = ((1F - previousProgress) * fillSize) - gapWidth,
                color = trackColor,
                stroke = if (selectedCategory is SelectedCategory.Remaining) selectedStroke else stroke
            )
        }
    }

    Card(
        shape = MaterialTheme.shapes.medium
    ) {

    }

    LaunchedEffect(Unit) {
        progress = 1F
    }
}

sealed class SelectedCategory {
    object None : SelectedCategory()
    object Remaining: SelectedCategory()
    data class Category(val expenseCategory: ExpenseCategory) : SelectedCategory()
}

private fun DrawScope.drawDeterminateCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) = drawCircularIndicator(startAngle, sweep, color, stroke)

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = 0F
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}