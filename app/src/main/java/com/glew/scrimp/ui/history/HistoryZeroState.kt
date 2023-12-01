package com.glew.scrimp.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.material.icons.twotone.Android
import androidx.compose.material.icons.twotone.AutoAwesome
import androidx.compose.material.icons.twotone.Commute
import androidx.compose.material.icons.twotone.TravelExplore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.glew.scrimp.ui.common.ZeroState

@Composable
fun HistoryZeroState(
    onViewCurrentTripClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ZeroState(
            icon = Icons.Rounded.TravelExplore,
            title = "No past trips",
            body = "View your archived trips here.",
            buttonText = "Go to dashboard",
            onButtonClick = onViewCurrentTripClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryZeroStatePreview() {
    HistoryZeroState {}
}