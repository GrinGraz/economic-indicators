package cl.cruz.economicindicators.domain.mapper

import cl.cruz.economicindicators.data.model.remote.EconomicIndicatorsResponse
import cl.cruz.economicindicators.data.model.remote.Indicator
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel

fun Indicator?.toEconomicIndicatorModel(): EconomicIndicatorModel =
    EconomicIndicatorModel(
        code = this?.code ?: "",
        name = this?.name ?: "",
        measureUnit = this?.measureUnit ?: "",
        date = this?.date ?: "",
        value = this?.value ?: 0.0
    )

fun EconomicIndicatorsResponse?.toEconomicIndicatorsModel(): List<EconomicIndicatorModel> {
    val indicators = mutableListOf<EconomicIndicatorModel?>()
    this?.let {
        with(indicators) {
            add(bitcoin?.toEconomicIndicatorModel())
            add(copperPound?.toEconomicIndicatorModel())
            add(dolar?.toEconomicIndicatorModel())
            add(dolarExchange?.toEconomicIndicatorModel())
            add(euro?.toEconomicIndicatorModel())
            add(imacec?.toEconomicIndicatorModel())
            add(ipc?.toEconomicIndicatorModel())
            add(ivp?.toEconomicIndicatorModel())
            add(tpm?.toEconomicIndicatorModel())
            add(uf?.toEconomicIndicatorModel())
            add(unemploymentRate?.toEconomicIndicatorModel())
            add(utm?.toEconomicIndicatorModel())
        }
    }
    return indicators.filterNotNull()
}