package com.glew.scrimp.ui.common

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun AutoSizeText(
    text: String,
    style: TextStyle,
) {
    var readyToDraw by remember {
        mutableStateOf(false)
    }

    var multiplier by remember { mutableStateOf(1f) }

    Text(
        text = text,
        maxLines = 1, // modify to fit your need
        overflow = TextOverflow.Visible,
        style = style.copy(
            fontSize = style.fontSize * multiplier
        ),
        modifier = Modifier.drawWithContent { if (readyToDraw) drawContent() },
        onTextLayout = {
            if (it.hasVisualOverflow) {
                multiplier *= 0.9f // you can tune this constant
            } else {
                readyToDraw = true
            }
        }
    )
}