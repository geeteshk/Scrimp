package com.glew.scrimp.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.getHarmonizedColor
import com.glew.scrimp.extensions.icon
import com.glew.scrimp.extensions.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseCategoryDropdown(
    selectedCategory: ExpenseCategory?,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val leadingIcon = selectedCategory?.icon
    val leadingIconTint = selectedCategory?.getHarmonizedColor() ?: LocalContentColor.current

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
        OutlinedTextField(
            value = selectedCategory?.title ?: "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = { Text(text = "Category") },
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(imageVector = leadingIcon, contentDescription = null, tint = leadingIconTint)
                }
            } else {
                null
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            singleLine = true,
            readOnly = true
        )
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }, modifier = Modifier.exposedDropdownSize()) {
            ExpenseCategory.sortedValues().forEach {
                DropdownMenuItem(
                    text = { Text(text = it.title) },
                    leadingIcon = { Icon(imageVector = it.icon, contentDescription = null, tint = it.getHarmonizedColor()) },
                    onClick = {
                        onCategorySelected(it)
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseTypeDropdownPreview() {
    Column {
        ExpenseCategory.sortedValues().forEach {
            ExpenseCategoryDropdown(
                selectedCategory = it,
                onCategorySelected = {}
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseTypeDropdownNothingSelectedPreview() {
    ExpenseCategoryDropdown(
        selectedCategory = null,
        onCategorySelected = {}
    )
}