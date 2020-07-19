package cl.cruz.economicindicators.domain.repository

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel

interface EconomicIndicatorsRepository {
    suspend fun getEconomicIndicators(): List<EconomicIndicatorModel>
    suspend fun getEconomicIndicatorDetail(code: String): EconomicIndicatorModel
    suspend fun saveEconomicIndicators(economicIndicators: List<EconomicIndicatorEntity>)
}
