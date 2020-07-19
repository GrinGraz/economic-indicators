package cl.cruz.economicindicators.presentation

import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel

fun EconomicIndicatorModel?.toEconomicIndicator(): EconomicIndicator =
    EconomicIndicator(
        code = this?.code ?: "",
        name = this?.name ?: "",
        value = this?.value ?: 0.0
    )