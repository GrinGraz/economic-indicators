package cl.cruz.economicindicators.domain.repository

import cl.cruz.economicindicators.data.model.Result
import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel

interface EconomicIndicatorsRepository {
    suspend fun getEconomicIndicators(forced: Boolean): Result<List<EconomicIndicatorModel>>
    suspend fun getEconomicIndicatorDetail(code: String): Result<EconomicIndicatorModel>
    suspend fun saveEconomicIndicators(economicIndicators: List<EconomicIndicatorEntity>)
}
