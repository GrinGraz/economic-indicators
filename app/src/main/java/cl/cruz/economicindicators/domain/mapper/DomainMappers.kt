package cl.cruz.economicindicators.domain.mapper

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
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

fun Indicator?.toEconomicIndicatorEntity(): EconomicIndicatorEntity =
    EconomicIndicatorEntity(
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

fun EconomicIndicatorsResponse?.toEconomicIndicatorsEntity(): List<EconomicIndicatorEntity> {
    val indicators = mutableListOf<EconomicIndicatorEntity?>()
    this?.let {
        with(indicators) {
            add(bitcoin?.toEconomicIndicatorEntity())
            add(copperPound?.toEconomicIndicatorEntity())
            add(dolar?.toEconomicIndicatorEntity())
            add(dolarExchange?.toEconomicIndicatorEntity())
            add(euro?.toEconomicIndicatorEntity())
            add(imacec?.toEconomicIndicatorEntity())
            add(ipc?.toEconomicIndicatorEntity())
            add(ivp?.toEconomicIndicatorEntity())
            add(tpm?.toEconomicIndicatorEntity())
            add(uf?.toEconomicIndicatorEntity())
            add(unemploymentRate?.toEconomicIndicatorEntity())
            add(utm?.toEconomicIndicatorEntity())
        }
    }
    return indicators.filterNotNull()
}