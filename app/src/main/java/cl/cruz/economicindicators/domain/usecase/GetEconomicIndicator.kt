package cl.cruz.economicindicators.domain.usecase

import cl.cruz.economicindicators.data.repository.EconomicIndicatorDataRepository
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository

class GetEconomicIndicator(private val repository: EconomicIndicatorsRepository = EconomicIndicatorDataRepository()) {

    suspend operator fun invoke(code: String): EconomicIndicatorModel {
        return repository.getEconomicIndicatorDetail(code)
    }
}