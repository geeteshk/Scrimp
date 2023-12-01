package com.glew.scrimp.ui.dashboard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BrowsePreviousTripsButton(
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(text = "Browse previous trips")
        Spacer(modifier = Modifier.width(8.dp))
        Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null)
    }
}

@Preview
@Composable
fun BrowsePreviousTripsButtonPreview() {
    BrowsePreviousTripsButton(onClick = {})
}