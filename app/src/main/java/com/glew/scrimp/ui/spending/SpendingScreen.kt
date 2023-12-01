package com.glew.scrimp.ui.spending

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glew.scrimp.ui.dashboard.DashboardZeroState
import com.glew.scrimp.ui.dashboard.InsightCard
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SpendingScreen(
    viewModel: SpendingViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Spending") },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        viewState?.let { viewState ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                /*val pagerState = rememberPagerState()
                HorizontalPager(
                    pageCount = viewState.insightCards.size,
                    state = pagerState,
                ) {
                    InsightCard(
                        modifier = Modifier/*.graphicsLayer {
                            val pageOffset = ((pagerState.currentPage - it) + pagerState.currentPageOffsetFraction).absoluteValue

                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                        }*/,
                        type = viewState.insightCards[it]
                    )
                }*/

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(viewState.insightCards) {
                        InsightCard(type = it)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Daily Spending",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DailySpendingChart(
                        stackedDailySpending = viewState.stackedDailySpending,
                        numberFormat = viewState.numberFormat,
                        startDate = viewState.startDate
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Spending Over Time",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SpendingOverTimeChart(
                        budget = viewState.budget,
                        numberFormat = viewState.numberFormat,
                        totalDailySpending = viewState.totalDailySpending,
                        startDate = viewState.startDate
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}