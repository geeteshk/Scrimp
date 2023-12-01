package com.glew.scrimp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RemoveReceiptDialog(
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(imageVector = Icons.Rounded.ReceiptLong, contentDescription = null) },
        title = { Text(text = "Remove receipt?") },
        text = { Text(text = "This will remove this receipt from this expense and allow you to attach a new one. This will not delete the image from your files.") },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = "Remove")
            }
        }
    )
}

@Preview
@Composable
fun RemoveReceiptDialogPreview() {
    RemoveReceiptDialog(
        onDismiss = {},
        onConfirmClick = {}
    )
}