package com.glew.scrimp.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.twotone.Android
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ZeroState(
    icon: ImageVector,
    title: String,
    body: String,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(180.dp)
                .alpha(0.38F)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = body,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = onButtonClick) {
            Text(text = buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ZeroStatePreview() {
    ZeroState(
        icon = Icons.TwoTone.Android,
        title = "No current trip",
        body = "Create a trip to start.",
        buttonText = "Create trip",
        onButtonClick = {}
    )
}