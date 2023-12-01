package com.glew.scrimp.ui.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glew.scrimp.ui.common.UnsavedChangesDialog
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@Composable
fun EditTripScreen(tripId: Int = -1) {

    val viewModel: EditTripViewModel = hiltViewModel()

    LaunchedEffect(tripId) {
        viewModel.initializeUi(tripId)
    }

    val viewState by viewModel.viewState

    BackHandler(
        enabled = true,
        onBack = viewModel::onBackPressed
    )

    EditTripContent(
        viewState = viewState,
        onNameChange = viewModel::onNameChange,
        onLocationChange = viewModel::onLocationChange,
        onCountryItemClick = viewModel::onCountryItemClick,
        onDateSelected = viewModel::onDateSelected,
        onBudgetChange = viewModel::onBudgetChange,
        onSaveClick = viewModel::onSaveClick,
        onCloseClick = viewModel::onBackPressed,
        onUnsavedChangesDialogDismiss = viewModel::onUnsavedChangesDialogDismiss,
        onUnsavedChangesDialogDiscardClick = viewModel::onUnsavedChangesDialogDiscardClick,
        onUnsavedChangesDialogSaveClick = viewModel::onUnsavedChangesDialogSaveClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTripContent(
    viewState: EditTripViewState,
    onNameChange: (TextFieldValue) -> Unit,
    onLocationChange: (TextFieldValue) -> Unit,
    onCountryItemClick: (CountryItem) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onBudgetChange: (TextFieldValue) -> Unit,
    onSaveClick: () -> Unit,
    onCloseClick: () -> Unit,
    onUnsavedChangesDialogDismiss: () -> Unit,
    onUnsavedChangesDialogDiscardClick: () -> Unit,
    onUnsavedChangesDialogSaveClick: () -> Unit
) {
    val isNewTrip = viewState.tripId == -1

    var isDatePickerVisible by remember {
        mutableStateOf(false)
    }

    Box {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        val title = if (isNewTrip) {
                            "New Trip"
                        } else {
                            "Edit Trip"
                        }

                        Text(text = title)
                    },
                    navigationIcon = {
                        IconButton(onClick = onCloseClick) {
                            Icon(imageVector = if (isNewTrip) Icons.Rounded.ArrowBack else Icons.Rounded.Close, contentDescription = null)
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = onSaveClick,
                            enabled = viewState.isSaveEnabled
                        ) {
                            Text(text = "Save")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    TripForm(
                        viewState = viewState,
                        onNameChange = onNameChange,
                        onLocationChange = onLocationChange,
                        onCountryItemClick = onCountryItemClick,
                        onDateClicked = { isDatePickerVisible = true },
                        onBudgetChange = onBudgetChange
                    )
                }
            }
        }

        if (isDatePickerVisible) {
            CalendarDialog(
                state = rememberUseCaseState(visible = true, onCloseRequest = { isDatePickerVisible = false }),
                config = CalendarConfig(
                    style = CalendarStyle.MONTH,
                    yearSelection = true,
                    monthSelection = true,
                ),
                selection = CalendarSelection.Date { onDateSelected(it) }
            )
        }

        if (viewState.isUnsavedChangesDialogVisible) {
            UnsavedChangesDialog(
                onDismiss = onUnsavedChangesDialogDismiss,
                onDiscardClick = onUnsavedChangesDialogDiscardClick,
                onSaveClick = onUnsavedChangesDialogSaveClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditTripScreenPreview() {
    EditTripScreen()
}