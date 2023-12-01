package com.glew.scrimp.ui.converter

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glew.scrimp.data.ExchangeRatesRepository
import com.glew.scrimp.data.TripRepository
import com.glew.scrimp.extensions.update
import com.neovisionaries.i18n.CurrencyCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val tripRepository: TripRepository
) : ViewModel() {

    private val _viewState = mutableStateOf(ConverterViewState())
    val viewState: State<ConverterViewState> = _viewState

    init {
        viewModelScope.launch {
            combine(
                exchangeRatesRepository.get(),
                tripRepository.getCurrentTrip()
            ) { rates, currentTrip ->
                val currentViewState = _viewState.value

                val countryCode = currentTrip?.countryCode ?: "us"
                val selectedCurrencyItem = CurrencyCode.getByCountry(countryCode, false)[0]

                val currencyItems = rates?.rates
                    ?.keys
                    ?.filterNot { it == selectedCurrencyItem.currency.currencyCode }
                    ?.map { CurrencyCode.getByCode(it, false) } ?: emptyList()

                currentViewState.copy(
                    selectedCurrency = selectedCurrencyItem,
                    currencies = currencyItems,
                    rateMap = rates?.rates ?: emptyMap()
                )
            }.collect {
                _viewState.value = it
            }
        }

        viewModelScope.launch {
            exchangeRatesRepository.load()
        }
    }

    fun onBigNumberClick() {
        _viewState.update {
            it.copy(isBigNumberSelected = true)
        }
    }

    fun onSmallNumberClick() {
        _viewState.update {
            it.copy(isBigNumberSelected = false)
        }
    }

    fun onKeypadButtonClick(button: ConverterButtonType) {

        val currentViewState = _viewState.value
        var bigNumber = currentViewState.bigNumber
        var smallNumber = currentViewState.smallNumber

        when (button) {
            ConverterButtonType.Backspace -> if (currentViewState.isBigNumberSelected) {
                bigNumber /= 10u
            } else {
                smallNumber /= 10u
            }
            is ConverterButtonType.Number -> if (currentViewState.isBigNumberSelected) {
                bigNumber = (bigNumber * 10u) + button.value
            } else {
                val newSmallNumber = (smallNumber * 10u) + button.value
                smallNumber = if (newSmallNumber > 99u) {
                    newSmallNumber % 100u
                } else {
                    newSmallNumber
                }
            }
            ConverterButtonType.Empty -> { /* NOOP */ }
        }

        _viewState.update {
            it.copy(
                bigNumber = bigNumber,
                smallNumber = smallNumber
            )
        }
    }

    fun onCurrencyClick(item: CurrencyCode) {
        _viewState.update {
            it.copy(selectedCurrency = item)
        }
    }
}