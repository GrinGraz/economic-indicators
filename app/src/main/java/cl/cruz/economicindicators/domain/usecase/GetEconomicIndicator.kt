package cl.cruz.economicindicators.domain.usecase

import cl.cruz.economicindicators.data.model.Result
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository

class GetEconomicIndicator(private val repository: EconomicIndicatorsRepository) {
    suspend operator fun invoke(code: String): Result<EconomicIndicatorModel> =
        repository.getEconomicIndicatorDetail(code)
}