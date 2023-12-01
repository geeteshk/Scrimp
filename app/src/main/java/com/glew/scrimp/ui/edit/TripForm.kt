package com.glew.scrimp.ui.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.glew.scrimp.extensions.defaultFormat
import com.glew.scrimp.ui.common.FlagImage
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripForm(
    viewState: EditTripViewState,
    onNameChange: (TextFieldValue) -> Unit,
    onLocationChange: (TextFieldValue) -> Unit,
    onCountryItemClick: (CountryItem) -> Unit,
    onDateClicked: () -> Unit,
    onBudgetChange: (TextFieldValue) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = viewState.name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        val isExpanded = viewState.countries.isNotEmpty()

        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {}) {
            OutlinedTextField(
                value = viewState.location,
                onValueChange = onLocationChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text(text = "Location") },
                leadingIcon = {
                    viewState.selectedCountry?.code?.let {
                        FlagImage(
                            countryCode = it,
                            modifier = Modifier.size(24.dp)
                        )
                    } ?: run {
                        Icon(imageVector = Icons.Rounded.LocationCity, contentDescription = null)
                    }
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {}, modifier = Modifier.exposedDropdownSize()) {
                viewState.countries.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        leadingIcon = {
                                      FlagImage(
                                          countryCode = it.code ?: "us",
                                          modifier = Modifier.size(24.dp)
                                      )
                        },
                        onClick = { onCountryItemClick(it) },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        val dateFieldInteractionSource = remember { MutableInteractionSource() }
        val isPressed by dateFieldInteractionSource.collectIsPressedAsState()

        if (isPressed) {
            onDateClicked()
        }

        OutlinedTextField(
            value = viewState.date.defaultFormat(),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Date") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Event,
                    contentDescription = null
                )
            },
            readOnly = true,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            interactionSource = dateFieldInteractionSource
        )

        OutlinedTextField(
            value = viewState.budget,
            onValueChange = onBudgetChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Budget") },
            leadingIcon = {
                //Icon(
                //    imageVector = Icons.Rounded.AccountBalanceWallet,
                //    contentDescription = null
                //)

                viewState.selectedCountry?.currencySymbol?.let {
                    Text(text = it)
                }
            },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
        )
    }
}