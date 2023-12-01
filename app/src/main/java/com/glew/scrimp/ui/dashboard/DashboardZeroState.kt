package com.glew.scrimp.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.glew.scrimp.ui.common.ZeroState

@Composable
fun DashboardZeroState(
    isBrowsePreviousButtonVisible: Boolean,
    onCreateTripClick: () -> Unit,
    onBrowsePreviousTripsClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        if (isBrowsePreviousButtonVisible) {
            Spacer(modifier = Modifier.weight(1F))
        }

        ZeroState(
            icon = Icons.Rounded.Android,
            title = "No current trip",
            body = "Create a trip to start.",
            buttonText = "Create trip",
            onButtonClick = onCreateTripClick
        )

        if (isBrowsePreviousButtonVisible) {
            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.Center
            ) {
                BrowsePreviousTripsButton(
                    onClick = onBrowsePreviousTripsClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardZeroStatePreview() {
    DashboardZeroState(
        isBrowsePreviousButtonVisible = true,
        onCreateTripClick = {},
        onBrowsePreviousTripsClick = {}
    )
}