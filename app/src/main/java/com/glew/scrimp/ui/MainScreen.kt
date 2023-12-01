package com.glew.scrimp.ui

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.PostAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.glew.scrimp.MainViewModel
import com.glew.scrimp.Screen
import com.glew.scrimp.Screen.BottomNavScreen
import com.glew.scrimp.ui.common.ViewReceiptDialog
import com.glew.scrimp.ui.converter.ConverterScreen
import com.glew.scrimp.ui.dashboard.DashboardScreen
import com.glew.scrimp.ui.edit.EditTripScreen
import com.glew.scrimp.ui.history.HistoryScreen
import com.glew.scrimp.ui.spending.SpendingScreen
import com.glew.scrimp.ui.summary.ExpenseFormScreen
import com.glew.scrimp.ui.summary.ExpensesListScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigator: Navigator
) {
    val viewModel: MainViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    LaunchedEffect("navigation") {
        navigator.destinationScreen.onEach {
            when (it) {
                is Screen.Back -> navController.popBackStack()
                is BottomNavScreen -> {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
                else -> navController.navigate(it.formattedRoute)
            }
        }.launchIn(this)
    }

    val viewState by viewModel.viewState

    val bottomBarItems = buildList {
        add(BottomNavScreen.Dashboard)
        if (viewState.showSpendingTab) add(BottomNavScreen.Spending)
        add(BottomNavScreen.PastTrips)
        add(BottomNavScreen.Converter)
    }

    val fabBottomBarItems = listOf(
        BottomNavScreen.Dashboard
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var isBottomBarVisible by remember {
        mutableStateOf(true)
    }

    var isFloatingActionButtonVisible by remember {
        mutableStateOf(true)
    }

    val bottomBarRoutes = bottomBarItems.map { it.route }
    isBottomBarVisible = currentDestination?.route in bottomBarRoutes

    val fabRoutes = fabBottomBarItems.map { it.route }
    isFloatingActionButtonVisible = currentDestination?.route in fabRoutes

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                NavigationBar {
                    bottomBarItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                            label = { Text(text = stringResource(id = screen.labelId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                coroutineScope.launch {
                                    navigator.navigateTo(screen)
                                }
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isFloatingActionButtonVisible,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(onClick = {
                    viewModel.onNewExpenseClick()
                }) {
                    Icon(imageVector = Icons.Rounded.PostAdd, contentDescription = null)
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Dashboard.route) { DashboardScreen() }
            composable(BottomNavScreen.Spending.route) { SpendingScreen() }
            composable(BottomNavScreen.PastTrips.route) { HistoryScreen() }
            composable(BottomNavScreen.Converter.route) { ConverterScreen() }
            composable(
                route = Screen.ExpensesList().route,
                arguments = listOf(
                    navArgument("tripId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                ),
            ) {
                val tripId = it.arguments?.getInt("tripId") ?: -1
                ExpensesListScreen(tripId = tripId)
            }
            composable(
                route = Screen.Edit().route,
                arguments = listOf(
                    navArgument("tripId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                val tripId = it.arguments?.getInt("tripId") ?: -1

                EditTripScreen(tripId = tripId)
            }
            composable(
                route = Screen.Expense().route,
                arguments = listOf(
                    navArgument("tripId") {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument("expenseId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                val tripId = it.arguments?.getInt("tripId") ?: -1
                val expenseId = it.arguments?.getInt("expenseId") ?: -1

                ExpenseFormScreen(
                    tripId = tripId,
                    expenseId = expenseId
                )
            }
            dialog(
                route = Screen.ViewReceipt().route,
                arguments = listOf(
                    navArgument("receiptUri") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                ),
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                val receiptUri = Uri.parse(it.arguments?.getString("receiptUri") ?: "")

                ViewReceiptDialog(uri = receiptUri)
            }
        }
    }
}