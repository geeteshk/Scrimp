package com.glew.scrimp.ui.summary

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.PostAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.defaultFormat
import com.glew.scrimp.extensions.icon
import com.glew.scrimp.extensions.order
import com.glew.scrimp.extensions.title
import com.glew.scrimp.ui.common.DeleteExpenseDialog
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@Composable
fun ExpensesListScreen(tripId: Int = -1) {

    val expensesListViewModel: ExpensesListViewModel = hiltViewModel()

    LaunchedEffect(tripId) {
        expensesListViewModel.initializeUi(tripId)
    }

    val viewState by expensesListViewModel.viewState

    BackHandler(
        enabled = true,
        onBack = expensesListViewModel::onBackPressed
    )

    viewState?.let {
        ExpensesListContent(
            viewState = it,
            onBackClick = expensesListViewModel::onBackPressed,
            onExpenseItemClick = expensesListViewModel::onExpenseItemClick,
            onDeleteExpenseClick = expensesListViewModel::onDeleteExpenseClick,
            onLogExpenseClick = expensesListViewModel::onLogExpenseClick,
            onFilterSelected = expensesListViewModel::onFilterSelected,
            onFilterUnselected = expensesListViewModel::onFilterUnselected,
            onRemoveFiltersClick = expensesListViewModel::onRemoveFiltersClick,
            onPriceFilterValueChanged = expensesListViewModel::onPriceFilterValueChanged,
            onRemovePriceFilterClick = expensesListViewModel::onRemovePriceFilterClick,
            onDateRangeSelected = expensesListViewModel::onDateRangeSelected,
            onRemoveDateFilterClick = expensesListViewModel::onRemoveDateFilterClick,
        )
    } ?: run {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)
@Composable
fun ExpensesListContent(
    viewState: ExpensesListViewState,
    onBackClick: () -> Unit,
    onExpenseItemClick: (Int) -> Unit,
    onDeleteExpenseClick: (Int) -> Unit,
    onLogExpenseClick: () -> Unit,
    onFilterSelected: (ExpenseCategory) -> Unit,
    onFilterUnselected: (ExpenseCategory) -> Unit,
    onRemoveFiltersClick: () -> Unit,
    onPriceFilterValueChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    onRemovePriceFilterClick: () -> Unit,
    onDateRangeSelected: (ClosedRange<LocalDate>) -> Unit,
    onRemoveDateFilterClick: () -> Unit,
) {
    var isDatePickerVisible by remember {
        mutableStateOf(false)
    }

    val dateFieldInteractionSource = remember { MutableInteractionSource() }
    val dateIsPressed by dateFieldInteractionSource.collectIsPressedAsState()

    if (dateIsPressed) {
        isDatePickerVisible = true
    }

    var expenseIdToDelete by remember {
        mutableStateOf(-1)
    }

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    val extendedFabHeight = LocalDensity.current.run { 72.dp.toPx() }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val filteredItems = viewState.expenseItems
        .mapValues {
            it.value
                .filter { expenseItem ->
                    viewState.selectedFilters.isEmpty() || viewState.selectedFilters.contains(expenseItem.category)
                }
                .filter { expenseItem ->
                    expenseItem.numberAmount.toFloat() in viewState.selectedPriceRange
                }
        }
        .filterKeys { LocalDate.ofEpochDay(it) in viewState.selectedDateRange }
        .filterValues { it.isNotEmpty() }

    val filterSectionItems = buildList {
        if (viewState.selectedFilters.isNotEmpty()) add(FilterSelectionItem.CATEGORY)
        if (viewState.selectedPriceRange != viewState.minMaxPriceRange) add(FilterSelectionItem.PRICE)
        if (viewState.selectedDateRange != viewState.minMaxDateRange) add(FilterSelectionItem.DATE)
    }

    Box {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = { Text(text = "${viewState.name} Expenses") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { openBottomSheet = true }) {
                            Icon(
                                imageVector = Icons.Rounded.FilterList,
                                contentDescription = null
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                if (!viewState.isArchived) {
                    ExtendedFloatingActionButton(
                        onClick = onLogExpenseClick,
                        icon = { Icon(imageVector = Icons.Rounded.PostAdd, contentDescription = null) },
                        text = { Text(text = "Log expense") },
                        modifier = Modifier.graphicsLayer {
                            translationY = extendedFabHeight * topAppBarState.collapsedFraction
                        }
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val colorTransitionFraction = scrollBehavior.state.overlappedFraction
                val fraction = if (colorTransitionFraction > 0.01f) 1f else 0f
                val appBarContainerColor by animateColorAsState(
                    targetValue = lerp(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp),
                        FastOutLinearInEasing.transform(fraction)
                    ),
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    label = "Top App Bar Background"
                )

                AnimatedVisibility(
                    visible = filterSectionItems.isNotEmpty(),
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    LazyRow(
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth()
                            .background(appBarContainerColor),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                        ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = filterSectionItems,
                            key = { it }
                        ) {
                            when (it) {
                                FilterSelectionItem.CATEGORY -> CategoryFilterChip(
                                    numCategories = viewState.selectedFilters.size,
                                    onRemoveFiltersClick = onRemoveFiltersClick
                                )
                                FilterSelectionItem.PRICE -> PriceFilterChip(
                                    priceRangeText = viewState.priceRangeText,
                                    onRemovePriceFilterClick = onRemovePriceFilterClick
                                )
                                FilterSelectionItem.DATE -> FilterChip(
                                    selected = false,
                                    onClick = onRemoveDateFilterClick,
                                    label = { Text(text = viewState.dateRangeText) },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Rounded.Close,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                LazyColumn {
                    filteredItems.forEach { (epochDay, expenseItems) ->

                        val formattedDate = LocalDate.ofEpochDay(epochDay)
                            .defaultFormat()

                        stickyHeader {
                            Text(
                                text = formattedDate,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }

                        itemsIndexed(expenseItems) { index, expenseItem ->
                            ExpenseRow(
                                item = expenseItem,
                                onClick = { id -> if (!viewState.isArchived) { onExpenseItemClick(id) } },
                                onDeleteSwipe = { id -> expenseIdToDelete = id }
                            )

                            if (index < expenseItems.size - 1) {
                                Divider()
                            }
                        }
                    }
                }
            }
        }

        if (expenseIdToDelete != -1) {
            DeleteExpenseDialog(
                onDismiss = { expenseIdToDelete = -1 },
                onConfirmClick = {
                    onDeleteExpenseClick(expenseIdToDelete)
                    expenseIdToDelete = -1
                }
            )
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                Text(
                    text = "Filter by category",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    ExpenseCategory.sortedValues()
                        .forEach {
                            val selected = viewState.selectedFilters.contains(it)
                            FilterChip(
                                selected = selected,
                                onClick = {
                                    if (selected) {
                                        onFilterUnselected(it)
                                    } else {
                                        onFilterSelected(it)
                                    }
                                },
                                label = { Text(text = it.title) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (selected) Icons.Rounded.Check else it.icon,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Filter by price",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(1F)
                    )

                    Text(
                        text = viewState.priceRangeText,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                
                RangeSlider(
                    value = viewState.selectedPriceRange,
                    onValueChange = onPriceFilterValueChanged,
                    valueRange = viewState.minMaxPriceRange,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Filter by date",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = viewState.dateRangeText,
                    onValueChange = {},
                    readOnly = true,
                    interactionSource = dateFieldInteractionSource,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        if (isDatePickerVisible) {
            CalendarDialog(
                state = rememberUseCaseState(
                    visible = true,
                    onCloseRequest = { isDatePickerVisible = false }
                ),
                config = CalendarConfig(
                    style = CalendarStyle.MONTH,
                    yearSelection = true,
                    monthSelection = true,
                    boundary = viewState.minMaxDateRange,
                ),
                selection = CalendarSelection.Period { startDate, endDate ->
                    onDateRangeSelected(startDate .. endDate)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.CategoryFilterChip(
    numCategories: Int,
    onRemoveFiltersClick: () -> Unit,
) {
    FilterChip(
        selected = false,
        onClick = onRemoveFiltersClick,
        label = { Text(text = "$numCategories categories selected") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null
            )
        },
        modifier = Modifier.animateItemPlacement()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.PriceFilterChip(
    priceRangeText: String,
    onRemovePriceFilterClick: () -> Unit,
) {
    FilterChip(
        selected = false,
        onClick = onRemovePriceFilterClick,
        label = { Text(text = priceRangeText) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null
            )
        },
        modifier = Modifier.animateItemPlacement()
    )
}

@Preview(showBackground = true)
@Composable
fun ExpensesListScreenPreview() {
    ExpensesListScreen()
}