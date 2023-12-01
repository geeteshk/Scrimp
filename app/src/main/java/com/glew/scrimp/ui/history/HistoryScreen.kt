package com.glew.scrimp.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glew.scrimp.ui.common.DeleteTripDialog

@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel = hiltViewModel()) {

    val viewState by historyViewModel.viewState

    HistoryContent(
        viewState = viewState,
        onViewCurrentTripClick = historyViewModel::onViewCurrentTripClick,
        onCreateTripClick = historyViewModel::onCreateTripClick,
        onItemClick = historyViewModel::onItemClick,
        onDeleteTripClick = historyViewModel::onDeleteTripClick,
        onFavoriteClick = historyViewModel::onFavoriteClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    viewState: HistoryViewState,
    onViewCurrentTripClick: () -> Unit,
    onCreateTripClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onDeleteTripClick: (Int) -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit
) {
    var tripIdToDelete by remember {
        mutableStateOf(-1) // TODO: No more long click
    }

    var expandedId by remember {
        mutableStateOf(-1)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Past Trips") })
        }
    ) { padding ->
        if (viewState.items.isEmpty()) {
            if (viewState.hasCurrentTrip) {
                HistoryZeroState(onViewCurrentTripClick = onViewCurrentTripClick)
            } else {
                HistoryNoCurrentZeroState(onCreateTripClick = onCreateTripClick)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(viewState.items) {
                    val expanded = it.id == expandedId
                    PastTripCard(
                        model = it,
                        expanded = expanded,
                        onClick = { expandedId = if (expanded) -1 else it.id },
                        onSeeAllClick = { onItemClick(it.id) },
                        onFavoriteClick = { checked -> onFavoriteClick(it.id, checked) }
                    )
                }
            }
        }
    }

    if (tripIdToDelete != -1) {
        DeleteTripDialog(
            onDismiss = { tripIdToDelete = -1 },
            onConfirmClick = {
                onDeleteTripClick(tripIdToDelete)
                tripIdToDelete = -1
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    HistoryScreen()
}