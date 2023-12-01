package com.glew.scrimp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ArchiveConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(imageVector = Icons.Rounded.Archive, contentDescription = null) },
        title = { Text(text = "Archive trip?") },
        text = { Text(text = "This will archive your current trip and allow you to create a new one. You can view your past trips in History.") },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = "Archive")
            }
        }
    )
}

@Preview
@Composable
fun ArchiveConfirmationDialogPreview() {
    ArchiveConfirmationDialog(
        onDismiss = {},
        onConfirmClick = {}
    )
}