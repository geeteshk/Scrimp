package com.glew.scrimp

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.twotone.Backpack
import androidx.compose.material.icons.twotone.CurrencyExchange
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.Payments
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(open val route: String, val formattedRoute: String = route) {

    sealed class BottomNavScreen(override val route: String, @StringRes val labelId: Int, val icon: ImageVector) : Screen(route) {
        object Dashboard : BottomNavScreen("dashboard", R.string.screen_label_dashboard, Icons.TwoTone.Dashboard)
        object Spending : BottomNavScreen("spending", R.string.screen_label_spending, Icons.TwoTone.Payments)
        object PastTrips : BottomNavScreen("past_trips", R.string.screen_label_past_trips, Icons.TwoTone.Backpack)
        object Converter : BottomNavScreen("converter", R.string.screen_label_converter, Icons.TwoTone.CurrencyExchange)
    }

    data class ExpensesList(val tripId: Int = -1) : Screen("expenses_list?tripId={tripId}", formattedRoute = "expenses_list?tripId=$tripId")

    data class Edit(val tripId: Int = -1) : Screen("edit?tripId={tripId}", formattedRoute = "edit?tripId=$tripId")

    data class Expense(val tripId: Int = -1, val expenseId: Int = -1) : Screen("expense/{tripId}?expenseId={expenseId}", formattedRoute = "expense/$tripId?expenseId=$expenseId")

    data class ViewReceipt(val receiptUri: String = "") : Screen("receipt?receiptUri={receiptUri}", formattedRoute = "receipt?receiptUri=$receiptUri")

    object Back : Screen("")
}
