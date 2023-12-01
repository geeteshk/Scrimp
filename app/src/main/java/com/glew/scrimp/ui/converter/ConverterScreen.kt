package com.glew.scrimp.ui.converter

import android.util.Log
import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleUp
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.glew.scrimp.countryFlag
import com.glew.scrimp.ui.common.FlagImage
import com.glew.scrimp.ui.common.FlagImages
import com.glew.scrimp.ui.converter.ConverterButtonType.*
import com.neovisionaries.i18n.CurrencyCode
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(viewModel: ConverterViewModel = hiltViewModel()) {

    val viewState by viewModel.viewState

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val listState = rememberLazyListState()
    val view = LocalView.current

    BackHandler(enabled = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
        scope.launch {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }

    val bigNumberAlpha by animateFloatAsState(
        targetValue = if (viewState.isBigNumberSelected) 1F else 0.38F,
        label = "Big Number Alpha"
    )

    val smallNumberAlpha by animateFloatAsState(
        targetValue = if (!viewState.isBigNumberSelected) 1F else 0.38F,
        label = "Small Number Alpha"
    )

    val textSize by animateFloatAsState(
        targetValue = getTextSize(viewState.bigNumber.toString().length),
        label = "Text Size"
    )
    val textStyle = MaterialTheme.typography.displayLarge.copy(fontSize = textSize.sp)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 120.dp,
        sheetContent = {
            LazyColumn(
                state = listState
            ) {
                items(viewState.currencies) {
                    val mapping = viewState.rateMap[it.name.lowercase()]?.div(viewState.rateMap[viewState.selectedCurrency.name.lowercase()] ?: 1.0)
                    CurrencyRow(
                        item = it,
                        selectedCode = viewState.selectedCurrency.name,
                        selectedMapping = mapping,
                        amount = mapping?.times(viewState.getEnteredAmount()) ?: 0.0,
                        onClick = {
                            scope.launch {
                                scaffoldState.bottomSheetState.partialExpand()
                            }

                            scope.launch {
                                listState.scrollToItem(0)
                            }

                            viewModel.onCurrencyClick(it)
                        }
                    )
                }
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier.weight(1F),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text(
                        text = viewState.selectedCurrency.name,
                        style = textStyle,
                        modifier = Modifier
                            .alpha(0.38F)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = viewState.bigNumber.toString(),
                        style = textStyle.copy(fontWeight = if (viewState.isBigNumberSelected) FontWeight.Medium else FontWeight.Normal),
                        modifier = Modifier
                            .alpha(bigNumberAlpha)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = viewModel::onBigNumberClick
                            )
                    )

                    Text(
                        text = ".",
                        style = textStyle,
                        modifier = Modifier.alpha(0.38F)
                    )

                    Text(
                        text = viewState.smallNumber.toString().padStart(2, '0'),
                        style = textStyle.copy(fontWeight = if (!viewState.isBigNumberSelected) FontWeight.Medium else FontWeight.Normal),
                        modifier = Modifier
                            .alpha(smallNumberAlpha)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = viewModel::onSmallNumberClick
                            )
                    )
                }
            }

            ConverterButtons(onButtonClick = {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                viewModel.onKeypadButtonClick(it)
            })
        }
    }
}

@Composable
fun ConverterButtons(
    onButtonClick: (ConverterButtonType) -> Unit
) {
    val buttonRows = listOf(
        Number(1u),
        Number(2u),
        Number(3u),
        Number(4u),
        Number(5u),
        Number(6u),
        Number(7u),
        Number(8u),
        Number(9u),
        Empty,
        Number(0u),
        Backspace
    ).chunked(3)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(
            start = 48.dp,
            end = 48.dp,
            bottom = 16.dp
        )
    ) {
        buttonRows.forEach { buttons ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                buttons.forEach { button ->
                    when (button) {
                        Empty -> Spacer(modifier = Modifier.weight(1F))
                        Backspace -> FilledIconButton(
                            onClick = { onButtonClick(button) },
                            modifier = Modifier
                                .weight(1F)
                                .aspectRatio(1F)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Backspace,
                                contentDescription = null
                            )
                        }
                        is ConverterButtonType.Number -> FilledTonalButton(
                            onClick = { onButtonClick(button) },
                            modifier = Modifier
                                .weight(1F)
                                .aspectRatio(1F)
                        ) {
                            Text(
                                text = "${button.value}",
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyRow(
    item: CurrencyCode,
    selectedCode: String,
    selectedMapping: Double?,
    amount: Double,
    onClick: () -> Unit
) {
    val countryList = item.countryList.map { it.alpha2 }
    ListItem(
        headlineContent = { Text(text = item.currency.displayName) },
        leadingContent = {
            if (countryList.size > 1) {
                FlagImages(
                    countryCodes = countryList,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                FlagImage(
                    countryCode = countryList[0],
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        trailingContent = { Text(text = NumberFormat.getCurrencyInstance(Locale("en", countryList[0])).format(amount)) },
        overlineContent = { Text(text = "1 $selectedCode = ${"%.2f".format(selectedMapping)} ${item.name}") },
        modifier = Modifier.clickable { onClick() }
    )
}

fun getTextSize(bigNumberLength: Int): Float {
    return when {
        bigNumberLength < 5 -> 57F
        else -> (57F - (bigNumberLength * 2))
    }
}

sealed class ConverterButtonType {
    data class Number(val value: ULong) : ConverterButtonType()
    object Backspace : ConverterButtonType()
    object Empty : ConverterButtonType()
}