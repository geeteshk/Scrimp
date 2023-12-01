package com.glew.scrimp.extensions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiTransportation
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.Hotel
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.TheaterComedy
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import com.glew.scrimp.data.ExpenseCategory
import com.google.android.material.color.MaterialColors

val ExpenseCategory.title: String
    get() = when (this) {
        ExpenseCategory.ACCOMMODATION -> "Accommodation"
        ExpenseCategory.TRANSPORTATION -> "Transportation"
        ExpenseCategory.FOOD -> "Food"
        ExpenseCategory.ENTERTAINMENT -> "Entertainment"
        ExpenseCategory.SHOPPING -> "Shopping"
        ExpenseCategory.OTHER -> "Other"
    }

val ExpenseCategory.icon: ImageVector
    get() = when (this) {
        ExpenseCategory.ACCOMMODATION -> Icons.Rounded.Hotel
        ExpenseCategory.TRANSPORTATION -> Icons.Rounded.EmojiTransportation
        ExpenseCategory.FOOD -> Icons.Rounded.Fastfood
        ExpenseCategory.ENTERTAINMENT -> Icons.Rounded.TheaterComedy
        ExpenseCategory.SHOPPING -> Icons.Rounded.ShoppingBag
        ExpenseCategory.OTHER -> Icons.Rounded.Money
    }

private val ExpenseCategory.color: Color
    get() = when (this) {
        ExpenseCategory.ACCOMMODATION -> Color(0xFF81C784)
        ExpenseCategory.TRANSPORTATION -> Color(0xFF64B5F6)
        ExpenseCategory.FOOD -> Color(0xFFFFB74D)
        ExpenseCategory.ENTERTAINMENT -> Color(0xFFF06292)
        ExpenseCategory.SHOPPING -> Color(0xFF9575CD)
        ExpenseCategory.OTHER -> Color(0xFF90A4AE)
    }

@Composable
fun ExpenseCategory.getHarmonizedColor(): Color = Color(MaterialColors.harmonize(this.color.toArgb(), MaterialTheme.colorScheme.primary.toArgb()))

val ExpenseCategory.order: Int
    get() = when (this) {
        ExpenseCategory.ACCOMMODATION -> 4
        ExpenseCategory.TRANSPORTATION -> 3
        ExpenseCategory.FOOD -> 5
        ExpenseCategory.ENTERTAINMENT -> 1
        ExpenseCategory.SHOPPING -> 2
        ExpenseCategory.OTHER -> 6
    }