package com.glew.scrimp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DeleteTripDialog(
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(imageVector = Icons.Rounded.DeleteOutline, contentDescription = null) },
        title = { Text(text = "Delete trip?") },
        text = { Text(text = "This action cannot be undone.") },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = "Delete")
            }
        }
    )
}

@Preview
@Composable
fun DeleteTripDialogPreview() {
    DeleteTripDialog(
        onDismiss = {},
        onConfirmClick = {}
    )
}