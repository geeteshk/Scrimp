package com.glew.scrimp.ui.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.LineAxis
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.PostAdd
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.TheaterComedy
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material.icons.twotone.PostAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glew.scrimp.R
import com.glew.scrimp.extensions.getHarmonizedColor
import com.glew.scrimp.extensions.icon
import com.glew.scrimp.extensions.title
import com.glew.scrimp.ui.common.ArchiveConfirmationDialog
import com.glew.scrimp.ui.common.AutoSizeText
import com.glew.scrimp.ui.common.FlagImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(dashboardViewModel: DashboardViewModel = hiltViewModel()) {

    val viewState by dashboardViewModel.viewState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val title = viewState.card?.name ?: "Dashboard"
    val subtitle = viewState.card?.location?.let {
        "to $it"
    }

    var isArchiveConfirmationVisible by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            viewState.card?.let {
                TopAppBar(
                    title = {
                        Column {
                            Text(text = title)
                            subtitle?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        FlagImage(
                            countryCode = it.countryCode,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(8.dp)
                        )
                    },
                    actions = {
                        var expanded by remember { mutableStateOf(false) }

                        IconButton(onClick = dashboardViewModel::onMoreClick) {
                            Icon(Icons.Rounded.ReceiptLong, contentDescription = null)
                        }

                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Rounded.MoreVert, contentDescription = null)
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit") },
                                onClick = dashboardViewModel::onEditClick,
                                leadingIcon = {
                                    Icon(
                                        Icons.Rounded.Edit,
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Archive") },
                                onClick = { isArchiveConfirmationVisible = true },
                                leadingIcon = {
                                    Icon(
                                        Icons.Rounded.Archive,
                                        contentDescription = null
                                    )
                                }
                            )
                        }

                    },
                    scrollBehavior = scrollBehavior
                )
            } ?: run {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) }
                )
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Box(modifier = Modifier.padding(it)) {
            viewState.card?.let { card ->
                DashboardContent(
                    card = card,
                    isBrowsePreviousButtonVisible = viewState.isBrowsePreviousTripsVisible,
                    onMoreClick = dashboardViewModel::onMoreClick,
                    onBrowsePreviousTripsClick = dashboardViewModel::onBrowsePreviousTripsClick,
                    onExpenseItemClick = dashboardViewModel::onExpenseItemClick,
                    onAddExpenseClick = dashboardViewModel::onAddExpenseClick,
                )
            } ?: run {
                DashboardZeroState(
                    isBrowsePreviousButtonVisible = viewState.isBrowsePreviousTripsVisible,
                    onCreateTripClick = dashboardViewModel::onCreateTripClick,
                    onBrowsePreviousTripsClick = dashboardViewModel::onBrowsePreviousTripsClick
                )
            }
        }
    }

    if (isArchiveConfirmationVisible) {
        ArchiveConfirmationDialog(
            onDismiss = { isArchiveConfirmationVisible = false },
            onConfirmClick = {
                isArchiveConfirmationVisible = false
                dashboardViewModel.onArchiveClick()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun DashboardContent(
    card: DashboardCard,
    isBrowsePreviousButtonVisible: Boolean,
    onMoreClick: () -> Unit,
    onBrowsePreviousTripsClick: () -> Unit,
    onExpenseItemClick: (Int) -> Unit,
    onAddExpenseClick: () -> Unit
) {
    var selectedCategory by remember {
        mutableStateOf<SelectedCategory>(SelectedCategory.None)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    ExpensesCircularProgressIndicator(
                        progresses = card.progresses,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1F),
                        selectedCategory = selectedCategory,
                        onCategoryClick = {
                            selectedCategory = it
                        }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedContent(
                            targetState = selectedCategory,
                            label = "Expense Section",
                            contentAlignment = Alignment.CenterStart
                        ) { category ->
                            when (category) {
                                is SelectedCategory.Category -> Text(
                                    text = category.expenseCategory.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = category.expenseCategory.getHarmonizedColor()
                                )
                                SelectedCategory.None -> Text(
                                    text = "Total Expenses",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                SelectedCategory.Remaining -> Text(
                                    text = "Remaining Balance",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }

                        AnimatedContent(
                            targetState = selectedCategory,
                            label = "Expense Section",
                            contentAlignment = Alignment.CenterStart
                        ) { category ->
                            when (category) {
                                is SelectedCategory.Category -> AutoSizeText(
                                    text = card.expenseBreakdownMap[category.expenseCategory]
                                        ?: "",
                                    style = MaterialTheme.typography.displayLarge
                                )
                                SelectedCategory.None -> AutoSizeText(
                                    text = "${card.spent} / ${card.budget}",
                                    style = MaterialTheme.typography.displayLarge
                                )
                                SelectedCategory.Remaining -> AutoSizeText(
                                    text = card.remaining,
                                    style = MaterialTheme.typography.displayLarge
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Today's Insights",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            card.insights?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    DashboardInsightCard(
                        modifier = Modifier.weight(1F),
                        icon = Icons.Rounded.TrendingUp,
                        label = "Spending",
                        body = it.todaySpent
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    DashboardInsightCard(
                        modifier = Modifier.weight(1F),
                        icon = it.highestCategory.icon,
                        label = "Highest Category",
                        body = it.highestCategory.title
                    )
                }
            } ?: run {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(imageVector = Icons.Rounded.CalendarToday, contentDescription = null, modifier = Modifier
                        .size(100.dp)
                        .alpha(0.38F))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No expenses logged today.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    TextButton(onClick = onAddExpenseClick) {
                        Text(text = "Add expense")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Recent Expenses",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (card.recentExpenses.isNotEmpty()) {
                card.recentExpenses.forEach {
                    DashboardExpenseRow(
                        item = it,
                        onClick = {
                            onExpenseItemClick(it.id)
                        }
                    )
                }

                Button(
                    onClick = onMoreClick,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "See all")
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(imageVector = Icons.Rounded.FormatListBulleted, contentDescription = null, modifier = Modifier
                        .size(100.dp)
                        .alpha(0.38F))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No expenses logged.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    TextButton(onClick = onAddExpenseClick) {
                        Text(text = "Add expense")
                    }
                }
            }

            if (isBrowsePreviousButtonVisible) {
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    BrowsePreviousTripsButton(
                        onClick = onBrowsePreviousTripsClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}