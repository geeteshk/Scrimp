package com.glew.scrimp.ui.history

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glew.scrimp.data.ExpenseCategory
import com.glew.scrimp.extensions.getHarmonizedColor
import com.glew.scrimp.extensions.icon
import com.glew.scrimp.extensions.title
import com.glew.scrimp.ui.summary.ExpensesLinearProgressIndicator
import com.glew.scrimp.ui.theme.PositiveVariance
import com.glew.scrimp.ui.theme.harmonizeWithPrimary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PastTripCard(
    model: HistoryItem,
    expanded: Boolean,
    onClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    onFavoriteClick: (Boolean) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Text(
                        text = model.title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Card {
                            Text(
                                text = "${model.location} ${model.countryFlag}",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Card {
                            Text(
                                text = model.date,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }

                FilledTonalIconToggleButton(checked = model.isFavorite, onCheckedChange = onFavoriteClick) {
                    AnimatedContent(
                        targetState = model.isFavorite,
                        label = "Trip Favorite",
                        transitionSpec = { fadeIn() with fadeOut() }
                    ) {
                        Icon(
                            imageVector = if (it) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpensesLinearProgressIndicator(
                progresses = model.progresses,
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    ExpenseCategory.sortedValues().forEach { category ->
                        val amount = model.expenseBreakdownMap[category] ?: model.formattedZero
                        ExpenseCategoryRow(category = category, amount = amount)
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1F)
                        )

                        Text(
                            text = model.spent,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Budget",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1F)
                        )

                        Text(
                            text = model.budget,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Variance",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1F)
                        )

                        Text(
                            text = buildString {
                                if (model.isVariancePositive) {
                                    append('+')
                                } else {
                                    append('-')
                                }

                                append(model.variance)
                            },
                            style = MaterialTheme.typography.labelLarge,
                            color = if (model.isVariancePositive) PositiveVariance.harmonizeWithPrimary() else MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = onSeeAllClick) {
                        Text(text = "See all")
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseCategoryRow(
    category: ExpenseCategory,
    amount: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = category.icon,
            contentDescription = null,
            tint = category.getHarmonizedColor()
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = category.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1F)
        )

        Text(
            text = amount,
            style = MaterialTheme.typography.labelMedium
        )
    }
}