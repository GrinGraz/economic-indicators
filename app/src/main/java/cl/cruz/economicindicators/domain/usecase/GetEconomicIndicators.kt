package cl.cruz.economicindicators.domain.usecase

import cl.cruz.economicindicators.di.injector
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository

class GetEconomicIndicators(private val repository: EconomicIndicatorsRepository = injector.repository) {

    suspend operator fun invoke(): List<EconomicIndicatorModel> {
        return repository.getEconomicIndicators()
    }
}