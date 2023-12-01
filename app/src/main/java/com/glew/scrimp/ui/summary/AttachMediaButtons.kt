package com.glew.scrimp.ui.summary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachMediaButtons(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    Row {
        OutlinedCard(
            onClick = onCameraClick,
            modifier = Modifier
                .weight(1F)
                .aspectRatio(1F),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.PhotoCamera,
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .alpha(0.6F)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedCard(
            onClick = onGalleryClick,
            modifier = Modifier
                .weight(1F)
                .aspectRatio(1F),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.AttachFile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .alpha(0.6F)
                )
            }
        }
    }
}

@Preview
@Composable
fun AttachMediaButtonsPreview() {
    AttachMediaButtons(
        onCameraClick = {},
        onGalleryClick = {}
    )
}