package cl.cruz.economicindicators.data.repository.datasource

import cl.cruz.economicindicators.data.model.remote.EconomicIndicatorsResponse
import cl.cruz.economicindicators.data.repository.datasource.remote.EconomicIndicatorsService
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.di.injector

class EconomicIndicatorsRemoteDataSource(
    private val economicIndicatorsService: EconomicIndicatorsService
) : RemoteDataSource {

    override suspend fun getEconomicIndicators(): EconomicIndicatorsResponse =
        economicIndicatorsService.getEconomicIndicators()

}