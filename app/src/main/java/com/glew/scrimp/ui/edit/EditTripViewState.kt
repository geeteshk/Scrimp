package com.glew.scrimp.ui.edit

import androidx.compose.ui.text.input.TextFieldValue
import com.glew.scrimp.countryFlag
import java.time.LocalDate
import java.util.*

data class EditTripViewState(
    val tripId: Int = -1,
    val name: TextFieldValue = TextFieldValue(),
    val location: TextFieldValue = TextFieldValue(),
    val selectedCountry: CountryItem? = null,
    val countries: List<CountryItem> = emptyList(),
    val date: LocalDate = LocalDate.now(),
    val budget: TextFieldValue = TextFieldValue(),
    val isSaveEnabled: Boolean = false,
    val isUnsavedChangesDialogVisible: Boolean = false,
)

data class CountryItem(
    val code: String?,
    val flag: String?,
    val name: String,
    val currencySymbol: String?,
) {

    companion object {
        fun fromCode(code: String): CountryItem {
            val locale = Locale("en", code)
            val currency = Currency.getInstance(locale)
            return CountryItem(
                code = code,
                flag = countryFlag(code),
                name = locale.displayCountry,
                currencySymbol = currency.symbol
            )
        }
    }
}