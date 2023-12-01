package com.glew.scrimp.ui.summary

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.getHarmonizedColor

@Composable
fun ExpensesLinearProgressIndicator(
    progresses: Map<ExpenseCategory, Float>,
    modifier: Modifier = Modifier,
    trackColor: Color = ProgressIndicatorDefaults.linearTrackColor,
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

    val colorMap = ExpenseCategory.sortedValues().associateWith {
        it.getHarmonizedColor()
    }

    val strokeWidth = LocalDensity.current.run { LinearIndicatorHeight.toPx() }

    Canvas(
        modifier
            .progressSemantics(progresses.values.sum())
            .size(width = LinearIndicatorWidth, height = LinearIndicatorHeight)
            .clip(RoundedCornerShape(percent = 50))
    ) {
        drawLinearIndicatorTrack(trackColor, strokeWidth)

        var previousProgress = 0F

        progresses.mapValues { it.value * animatedProgress }.entries.forEach { (category, progress) ->
            drawLinearIndicator(previousProgress, previousProgress + progress, colorMap[category] ?: trackColor, strokeWidth)
            previousProgress += progress
        }
    }

    LaunchedEffect(Unit) {
        progress = 1F
    }
}

private fun DrawScope.drawLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float
) {
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val barStart = startFraction * width
    val barEnd = endFraction * width

    // Progress line
    drawLine(color, Offset(barStart, yOffset), Offset(barEnd, yOffset), strokeWidth)
}

private fun DrawScope.drawLinearIndicatorTrack(
    color: Color,
    strokeWidth: Float
) = drawLinearIndicator(0f, 1f, color, strokeWidth)

internal val LinearIndicatorHeight = 4.dp
internal val LinearIndicatorWidth = 240.dp