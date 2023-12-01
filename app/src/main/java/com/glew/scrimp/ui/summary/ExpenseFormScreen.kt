package com.glew.scrimp.ui.summary

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.defaultFormat
import com.glew.scrimp.extensions.getTakePictureUri
import com.glew.scrimp.ui.common.ExpenseCategoryDropdown
import com.glew.scrimp.ui.common.RemoveReceiptDialog
import com.glew.scrimp.ui.common.UnsavedChangesDialog
import com.glew.scrimp.ui.theme.ScrimpTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun ExpenseFormScreen(tripId: Int, expenseId: Int = -1) {

    val expenseViewModel: ExpenseViewModel = hiltViewModel()

    LaunchedEffect(expenseId) {
        expenseViewModel.initializeUi(
            tripId = tripId,
            expenseId = expenseId
        )
    }

    val viewState by expenseViewModel.viewState

    ExpenseFormContent(
        viewState = viewState,
        onCloseClick = expenseViewModel::onCloseClick,
        onSaveClick = expenseViewModel::onSaveClick,
        onTitleChange = expenseViewModel::onTitleChange,
        onAmountChange = expenseViewModel::onAmountChange,
        onDateSelected = expenseViewModel::onDateSelected,
        onTimeSelected = expenseViewModel::onTimeSelected,
        onCategorySelected = expenseViewModel::onCategorySelected,
        onNotesChange = expenseViewModel::onNotesChange,
        onReceiptAttached = expenseViewModel::onReceiptAttached,
        onReceiptClick = expenseViewModel::onReceiptClick,
        onRemoveReceiptClick = expenseViewModel::onRemoveReceiptClick,
        onUnsavedChangesDialogDismiss = expenseViewModel::onUnsavedChangesDialogDismiss,
        onUnsavedChangesDialogDiscardClick = expenseViewModel::onUnsavedChangesDialogDiscardClick,
        onUnsavedChangesDialogSaveClick = expenseViewModel::onUnsavedChangesDialogSaveClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExpenseFormContent(
    viewState: ExpenseFormViewState,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (TextFieldValue) -> Unit,
    onAmountChange: (TextFieldValue) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    onCategorySelected: (ExpenseCategory) -> Unit,
    onNotesChange: (TextFieldValue) -> Unit,
    onReceiptAttached: (Uri?) -> Unit,
    onReceiptClick: (Uri) -> Unit,
    onRemoveReceiptClick: () -> Unit,
    onUnsavedChangesDialogDismiss: () -> Unit,
    onUnsavedChangesDialogDiscardClick: () -> Unit,
    onUnsavedChangesDialogSaveClick: () -> Unit
) {
    val isNewExpense = viewState.expenseId == -1

    var isDatePickerVisible by remember {
        mutableStateOf(false)
    }

    var isTimePickerVisible by remember {
        mutableStateOf(false)
    }

    var tempTakePictureUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            onReceiptAttached(tempTakePictureUri)
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        onReceiptAttached(uri)
    }

    val dateFieldInteractionSource = remember { MutableInteractionSource() }
    val timeFieldInteractionSource = remember { MutableInteractionSource() }

    val dateIsPressed by dateFieldInteractionSource.collectIsPressedAsState()
    val timeIsPressed by timeFieldInteractionSource.collectIsPressedAsState()

    when {
        dateIsPressed -> isDatePickerVisible = true
        timeIsPressed -> isTimePickerVisible = true
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isRemoveReceiptDialogVisible by remember {
        mutableStateOf(false)
    }

    BackHandler {
        onCloseClick()
    }

    Box {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (isNewExpense) {
                                "New Expense"
                            } else {
                                "Edit Expense"
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onCloseClick) {
                            Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = onSaveClick,
                            enabled = viewState.isSaveEnabled
                        ) {
                            Text(text = "Save")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = viewState.title,
                    onValueChange = onTitleChange,
                    label = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = viewState.amount,
                    onValueChange = onAmountChange,
                    label = { Text(text = "Amount") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.AttachMoney,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                )

                OutlinedTextField(
                    value = viewState.date.defaultFormat(),
                    onValueChange = {},
                    label = { Text(text = "Date") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Event,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    interactionSource = dateFieldInteractionSource
                )

                OutlinedTextField(
                    value = viewState.time.defaultFormat(),
                    onValueChange = {},
                    label = { Text(text = "Time") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.PunchClock,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    interactionSource = timeFieldInteractionSource
                )

                ExpenseCategoryDropdown(
                    selectedCategory = viewState.category,
                    onCategorySelected = onCategorySelected
                )

                OutlinedTextField(
                    value = viewState.notes,
                    onValueChange = onNotesChange,
                    label = { Text(text = "Notes (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Receipt",
                    style = MaterialTheme.typography.bodySmall
                )

                if (viewState.receiptUri != null) {
                    AsyncImage(
                        model = viewState.receiptUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2F)
                            .clip(RoundedCornerShape(12.dp))
                            .combinedClickable(
                                onClick = { onReceiptClick(viewState.receiptUri) },
                                onLongClick = { isRemoveReceiptDialogVisible = true }
                            ),
                        contentScale = ContentScale.FillWidth
                    )
                } else {
                    AttachMediaButtons(
                        onCameraClick = {
                            val uri = context.getTakePictureUri()
                            tempTakePictureUri = uri
                            cameraLauncher.launch(uri)
                        },
                        onGalleryClick = {
                            pickImageLauncher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    )
                }
            }
        }

        if (isDatePickerVisible) {
            CalendarDialog(
                state = rememberUseCaseState(
                    visible = true,
                    onCloseRequest = { isDatePickerVisible = false }
                ),
                config = CalendarConfig(
                    style = CalendarStyle.MONTH,
                    yearSelection = true,
                    monthSelection = true,
                ),
                selection = CalendarSelection.Date { onDateSelected(it) }
            )
        }

        if (isTimePickerVisible) {
            ClockDialog(
                state = rememberUseCaseState(
                    visible = true,
                    onCloseRequest = { isTimePickerVisible = false }
                ),
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    onTimeSelected(LocalTime.of(hours, minutes))
                },
                config = ClockConfig(is24HourFormat = false)
            )
        }

        if (isRemoveReceiptDialogVisible) {
            RemoveReceiptDialog(
                onDismiss = { isRemoveReceiptDialogVisible = false },
                onConfirmClick = {
                    isRemoveReceiptDialogVisible = false
                    onRemoveReceiptClick()
                }
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
