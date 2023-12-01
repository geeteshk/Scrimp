package com.glew.scrimp.ui.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.glew.scrimp.extensions.defaultFormat
import com.glew.scrimp.extensions.getHarmonizedColor
import com.glew.scrimp.extensions.icon

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DashboardExpenseRow(
    item: DashboardExpenseItem,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(text = item.title) },
        trailingContent = { Text(text = item.amount) },
        overlineContent = { Text(text = item.time.defaultFormat()) },
        leadingContent = {
            Icon(
                imageVector = item.category.icon,
                contentDescription = null,
                tint = item.category.getHarmonizedColor()
            )
        },
        /*trailingContent = {
            Icon(
                imageVector = Icons.Rounded.ArrowRight,
                contentDescription = null
            )
        },*/
        modifier = Modifier.clickable { onClick() }
    )
}