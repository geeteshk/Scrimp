package com.glew.scrimp.ui.summary

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glew.scrimp.Screen
import com.glew.scrimp.data.Expense
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.data.ExpenseRepository
import com.glew.scrimp.extensions.update
import com.glew.scrimp.ui.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _viewState = mutableStateOf(ExpenseFormViewState())
    val viewState: State<ExpenseFormViewState> = _viewState

    private var tripId = 0
    private lateinit var expenseFlow: StateFlow<Expense>

    fun initializeUi(tripId: Int, expenseId: Int) {

        this.tripId = tripId

        if (expenseId == -1) {
            _viewState.value = ExpenseFormViewState()
        } else {
            loadExpense(expenseId)
        }
    }

    private fun loadExpense(id: Int) = viewModelScope.launch {
        expenseFlow = repository.getExpense(id).stateIn(this)
        expenseFlow.map {

            val localDateTime = it.instant
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            ExpenseFormViewState(
                expenseId = id,
                title = TextFieldValue(it.title),
                amount = TextFieldValue(it.amount.toString()),
                date = localDateTime.toLocalDate(),
                time = localDateTime.toLocalTime(),
                category = it.category,
                notes = TextFieldValue(it.notes),
                isSaveEnabled = true,
                receiptUri = it.receiptUri,
            )
        }.collect {
            _viewState.value = it
        }
    }

    fun onCloseClick() = viewModelScope.launch {
        val viewState = _viewState.value
        when {
            viewState.expenseId == -1 || !viewState.isSaveEnabled || !haveFieldsChanged() -> navigator.navigateTo(Screen.Back)
            else -> _viewState.update {
                it.copy(isUnsavedChangesDialogVisible = true)
            }
        }

    }

    fun onUnsavedChangesDialogDismiss() {
        _viewState.update {
            it.copy(isUnsavedChangesDialogVisible = false)
        }
    }

    fun onUnsavedChangesDialogDiscardClick() = viewModelScope.launch {
        _viewState.update {
            it.copy(isUnsavedChangesDialogVisible = false)
        }

        navigator.navigateTo(Screen.Back)
    }

    fun onUnsavedChangesDialogSaveClick() {
        _viewState.update {
            it.copy(isUnsavedChangesDialogVisible = false)
        }

        onSaveClick()
    }

    private fun haveFieldsChanged(): Boolean {
        val old = expenseFlow.value
        val new = viewState.value

        val newInstant = LocalDateTime.of(new.date, new.time)
            .atZone(ZoneId.systemDefault())
            .toInstant()

        val hasTitleChanged = old.title != new.title.text
        val hasAmountChanged = old.amount != new.amount.text.toDoubleOrNull()
        val hasDateTimeChanged = old.instant != newInstant
        val hasCategoryChanged = old.category != new.category
        val hasNotesChanged = old.notes != new.notes.text
        val hasReceiptChanged = old.receiptUri != new.receiptUri

        return hasTitleChanged || hasAmountChanged || hasDateTimeChanged || hasCategoryChanged ||
                hasNotesChanged || hasReceiptChanged
    }

    fun onSaveClick() = viewModelScope.launch {
        val viewState = _viewState.value
        val id = when (viewState.expenseId) {
            -1 -> 0
            else -> viewState.expenseId
        }

        val instant = LocalDateTime.of(viewState.date, viewState.time)
            .atZone(ZoneId.systemDefault())
            .toInstant()

        val expense = Expense(
            id = id,
            title = viewState.title.text,
            amount = viewState.amount.text.toDouble(),
            instant = instant,
            notes = viewState.notes.text,
            category = viewState.category ?: ExpenseCategory.OTHER,
            receiptUri = viewState.receiptUri
        )

        when (viewState.expenseId) {
            -1 -> repository.createExpense(expense, tripId)
            else -> repository.updateExpense(expense, tripId)
        }

        navigator.navigateTo(Screen.Back)
    }

    private fun validateFields() {
        val viewState = _viewState.value
        val isTitleValid = viewState.title.text.isNotBlank()
        val amount = viewState.amount.text.toDoubleOrNull()
        val isAmountValid = amount != null && amount > 0

        _viewState.update {
            it.copy(isSaveEnabled = isTitleValid && isAmountValid)
        }
    }

    fun onTitleChange(title: TextFieldValue) {
        _viewState.update {
            it.copy(title = title)
        }

        validateFields()
    }

    fun onAmountChange(amount: TextFieldValue) {
        val regex = "\\d*(\\.\\d*)?\$".toRegex()

        if (amount.text.isNotEmpty() && !regex.containsMatchIn(amount.text)) return

        _viewState.update {
            it.copy(amount = amount)
        }

        validateFields()
    }

    fun onDateSelected(date: LocalDate) {
        _viewState.update {
            it.copy(date = date)
        }
    }

    fun onTimeSelected(time: LocalTime) {
        _viewState.update {
            it.copy(time = time)
        }
    }

    fun onCategorySelected(category: ExpenseCategory) {
        _viewState.update {
            it.copy(category = category)
        }
    }

    fun onNotesChange(notes: TextFieldValue) {
        _viewState.update {
            it.copy(notes = notes)
        }
    }

    fun onReceiptAttached(uri: Uri?) {
        _viewState.update {
            it.copy(receiptUri = uri)
        }
    }

    fun onReceiptClick(uri: Uri) = viewModelScope.launch {
        navigator.navigateTo(
            Screen.ViewReceipt(receiptUri = uri.toString())
        )
    }

    fun onRemoveReceiptClick() {
        _viewState.update {
            it.copy(receiptUri = null)
        }
    }
}