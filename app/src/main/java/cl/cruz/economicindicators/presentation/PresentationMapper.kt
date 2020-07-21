package cl.cruz.economicindicators.presentation

import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.ui.detail.EconomicIndicator

fun EconomicIndicatorModel?.toEconomicIndicator(): EconomicIndicator =
    EconomicIndicator(
        code = this?.code ?: "",
        name = this?.name ?: "",
        value = this?.value ?: 0.0,
        date = this?.date ?: "",
        measureUnit = this?.measureUnit ?: "unknown"
    )