package com.glew.scrimp.ui.edit

import com.glew.scrimp.SUPPORTED_COUNTRY_CODES
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glew.scrimp.Screen
import com.glew.scrimp.data.Trip
import com.glew.scrimp.data.TripRepository
import com.glew.scrimp.extensions.update
import com.glew.scrimp.ui.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditTripViewModel @Inject constructor(
    private val repository: TripRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val countryItems = SUPPORTED_COUNTRY_CODES.map {
        CountryItem.fromCode(it)
    }

    private val _viewState = mutableStateOf(EditTripViewState())
    val viewState: State<EditTripViewState> = _viewState

    private var tripJob: Job? = null
    private lateinit var tripFlow: StateFlow<Trip>

    fun initializeUi(tripId: Int) {
        if (tripId != -1) {
            viewModelScope.launch {
                loadTrip(tripId)
            }
        }
    }

    private suspend fun loadTrip(id: Int) {
        tripJob?.cancel()
        tripJob = viewModelScope.launch {
            tripFlow = repository.getTripWithExpenses(id).stateIn(this)
            tripFlow.map {
                val countryItem = CountryItem.fromCode(it.countryCode)

                EditTripViewState(
                    tripId = id,
                    name = TextFieldValue(it.name),
                    location = TextFieldValue(countryItem.name),
                    selectedCountry = countryItem,
                    countries = emptyList(),
                    date = it.date,
                    budget = TextFieldValue(it.budget.toString()),
                    isSaveEnabled = true,
                )
            }.collect {
                _viewState.value = it
            }
        }
    }

    private fun validateFields() {
        val viewState = _viewState.value
        val isNameValid = viewState.name.text.isNotBlank()
        val isLocationValid = viewState.selectedCountry != null
        val budget = viewState.budget.text.toDoubleOrNull()
        val isBudgetValid = budget != null && budget > 0

        _viewState.update {
            it.copy(isSaveEnabled = isNameValid && isLocationValid && isBudgetValid)
        }
    }

    fun onNameChange(name: TextFieldValue) {
        _viewState.update {
            it.copy(name = name)
        }

        validateFields()
    }

    fun onLocationChange(location: TextFieldValue) {
        val newItems = if (location.text.isNotBlank() && countryItems.none { it.name.lowercase() == location.text.lowercase() }) {
            countryItems.filter { it.name.lowercase().startsWith(location.text.lowercase()) }
        } else {
            emptyList()
        }

        _viewState.update {
            it.copy(
                location = location,
                selectedCountry = it.selectedCountry,
                countries = newItems,
            )
        }

        validateFields()
    }

    fun onCountryItemClick(item: CountryItem) {
        _viewState.update {
            it.copy(
                location = it.location.copy(text = item.name, selection = TextRange(item.name.length, item.name.length)),
                selectedCountry = item,
                countries = emptyList(),
            )
        }

        validateFields()
    }

    fun onDateSelected(date: LocalDate) {
        _viewState.update {
            it.copy(date = date)
        }
    }

    fun onBudgetChange(budget: TextFieldValue) {
        val regex = "\\d*(\\.\\d*)?\$".toRegex()

        if (budget.text.isNotEmpty() && !regex.containsMatchIn(budget.text)) return

        _viewState.update {
            it.copy(budget = budget)
        }

        validateFields()
    }

    fun onSaveClick() = viewModelScope.launch {
        val viewState = _viewState.value
        val id = when (viewState.tripId) {
            -1 -> 0
            else -> viewState.tripId
        }

        val trip = Trip(
            id = id,
            name = viewState.name.text,
            countryCode = viewState.selectedCountry?.code ?: "",
            date = viewState.date,
            budget = viewState.budget.text.toDouble(),
            isArchived = false,
            isFavorite = false
        )

        val newId = when (viewState.tripId) {
            -1 -> repository.createNewTrip(trip)
            else -> repository.updateTrip(trip)
        }

        navigator.navigateTo(Screen.Back)
        if (viewState.tripId == -1) {
            navigator.navigateTo(Screen.ExpensesList(newId))
        }
    }

    fun onBackPressed() = viewModelScope.launch {
        val viewState = _viewState.value
        when {
            viewState.tripId == -1 || !viewState.isSaveEnabled || !haveFieldsChanged()  -> navigator.navigateTo(Screen.Back)
            else -> _viewState.update {
                it.copy(isUnsavedChangesDialogVisible = true)
            }
        }
    }

    private fun haveFieldsChanged(): Boolean {
        val old = tripFlow.value
        val new = _viewState.value

        val hasNameChanged = old.name != new.name.text
        val hasLocationChanged = old.countryCode != new.selectedCountry?.code
        val hasDateChanged = old.date != new.date
        val hasBudgetChanged = old.budget != new.budget.text.toDoubleOrNull()

        return hasNameChanged || hasLocationChanged || hasDateChanged || hasBudgetChanged
    }

    fun onUnsavedChangesDialogDismiss() {
        _viewState.update {
            it.copy(isUnsavedChangesDialogVisible = false)
        }
    }

    fun onUnsavedChangesDialogDiscardClick() = viewModelScope.launch {
        _viewState.update {
            it.copy(isUnsavedChangesDialogVisible = false)
        }

        navigator.navigateTo(Screen.Back)
    }

    fun onUnsavedChangesDialogSaveClick() {
        _viewState.update {
            it.copy(isUnsavedChangesDialogVisible = false)
        }

        onSaveClick()
    }
}