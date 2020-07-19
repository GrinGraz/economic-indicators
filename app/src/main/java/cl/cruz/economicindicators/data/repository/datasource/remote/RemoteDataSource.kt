package cl.cruz.economicindicators.data.repository.datasource.remote

import cl.cruz.economicindicators.data.model.remote.EconomicIndicatorsResponse

interface RemoteDataSource {
    suspend fun getEconomicIndicators(): EconomicIndicatorsResponse
}