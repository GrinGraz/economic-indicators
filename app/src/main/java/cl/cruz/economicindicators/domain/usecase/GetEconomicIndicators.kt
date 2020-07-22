package cl.cruz.economicindicators.domain.usecase

import cl.cruz.economicindicators.data.model.Result
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository

class GetEconomicIndicators(private val repository: EconomicIndicatorsRepository) {
    suspend operator fun invoke(forced: Boolean): Result<List<EconomicIndicatorModel>> =
        repository.getEconomicIndicators(forced)

}