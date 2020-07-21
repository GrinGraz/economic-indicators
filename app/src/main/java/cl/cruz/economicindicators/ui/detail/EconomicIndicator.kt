package cl.cruz.economicindicators.ui.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EconomicIndicator(
    val code: String,
    val name: String,
    val value: Double,
    val measureUnit: String,
    val date: String
) : Parcelable

const val POSTFIX_CLP = "CLP"
const val POSTFIX_PERCENTAGE = "%"
const val POSTFIX_USD = "USD"

fun EconomicIndicator.formatIndicatorValue() = when(measureUnit) {
    "Pesos"-> "$$value $POSTFIX_CLP"
    "Porcentaje"-> "$value$POSTFIX_PERCENTAGE"
    "Dólar"-> "$$value $POSTFIX_USD"
    else -> "$value (Not formatted)"
}
