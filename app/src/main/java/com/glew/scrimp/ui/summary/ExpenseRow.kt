package com.glew.scrimp.ui.summary

import androidx.compose.animation.core.animate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.glew.scrimp.extensions.defaultFormat
import com.glew.scrimp.extensions.getHarmonizedColor
import com.glew.scrimp.extensions.icon
import kotlin.math.min
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseRow(
    item: ExpenseItem,
    onClick: (Int) -> Unit,
    onDeleteSwipe: (Int) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    var offsetX by remember { mutableStateOf(0f) }
    val sizeLimitOffset = -LocalDensity.current.run { 72.dp.toPx() }
    val deleteIconSize = 24.dp.times(min(offsetX / sizeLimitOffset, 1F))

    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Icon(
            imageVector = Icons.Rounded.DeleteOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .padding(end = 24.dp)
                .size(deleteIconSize)
                .align(Alignment.CenterEnd)
        )

        ListItem(
            headlineContent = { Text(text = item.title) },
            overlineContent = { Text(text = item.time.defaultFormat()) },
            leadingContent = {
                Icon(
                    imageVector = item.category.icon,
                    tint = item.category.getHarmonizedColor(),
                    contentDescription = null
                )
            },
            trailingContent = { Text(text = item.amount) },
            supportingContent = if (item.notes.isNotBlank()) {
                { Text(
                    text = item.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4F)
                ) }
            } else {
                null
            },
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .clickable { onClick(item.id) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState {
                        offsetX = (offsetX + it).coerceIn(
                            minimumValue = sizeLimitOffset,
                            maximumValue = 0F
                        )
                    },
                    onDragStopped = {
                        if (offsetX == sizeLimitOffset) {
                            onDeleteSwipe(item.id)
                        }

                        animate(
                            initialValue = offsetX,
                            targetValue = 0F
                        ) { value, _ ->
                            offsetX = value
                        }
                    }
                )
        )
    }

    LaunchedEffect(offsetX) {
        if (offsetX == sizeLimitOffset) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }
}