package cl.cruz.economicindicators.data.repository

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsLocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsRemoteDataSource
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.domain.EconomicIndicatorsRepository
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorModel
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorsModel
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel

class EconomicIndicatorDataRepository(
    private val remoteDataSource: RemoteDataSource = EconomicIndicatorsRemoteDataSource(),
    private val localDataSource: LocalDataSource = EconomicIndicatorsLocalDataSource()
) : EconomicIndicatorsRepository {

    override suspend fun getEconomicIndicators(): List<EconomicIndicatorModel> {
        val economicIndicatorEntities = remoteDataSource.getEconomicIndicators()
        return economicIndicatorEntities.toEconomicIndicatorsModel()
    }

    override suspend fun getEconomicIndicatorDetail(code: String): EconomicIndicatorModel {
        val localIndicatorEntity = localDataSource.findByCode(code)
        return localIndicatorEntity.toEconomicIndicatorModel()
    }

    override suspend fun saveEconomicIndicators(economicIndicators: List<EconomicIndicatorEntity>) {
        localDataSource.insertAll(*economicIndicators.toTypedArray())
    }
}
