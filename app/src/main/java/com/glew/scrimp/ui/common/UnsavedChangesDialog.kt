package com.glew.scrimp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UnsavedChangesDialog(
    onDismiss: () -> Unit,
    onDiscardClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(imageVector = Icons.Rounded.Save, contentDescription = null) },
        title = { Text(text = "Discard changes?") },
        text = { Text(text = "You have unsaved changes. Do you want to save or discard them?") },
        dismissButton = {
            TextButton(onClick = onDiscardClick) {
                Text(text = "Discard")
            }
        },
        confirmButton = {
            TextButton(onClick = onSaveClick) {
                Text(text = "Save")
            }
        }
    )
}

@Preview
@Composable
fun UnsavedChangesDialogPreview() {
    UnsavedChangesDialog(
        onDismiss = {},
        onDiscardClick = {},
        onSaveClick = {}
    )
}