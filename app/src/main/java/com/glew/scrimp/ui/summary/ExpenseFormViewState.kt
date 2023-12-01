package com.glew.scrimp.ui.summary

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.glew.scrimp.data.ExpenseCategory
import java.time.LocalDate
import java.time.LocalTime

data class ExpenseFormViewState(
    val expenseId: Int = -1,
    val title: TextFieldValue = TextFieldValue(),
    val amount: TextFieldValue = TextFieldValue(),
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val category: ExpenseCategory? = null,
    val notes: TextFieldValue = TextFieldValue(),
    val isSaveEnabled: Boolean = false,
    val receiptUri: Uri? = null,
    val isUnsavedChangesDialogVisible: Boolean = false,
)