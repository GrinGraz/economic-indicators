package cl.cruz.economicindicators.data.repository

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.di.injector
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorModel
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorsEntity
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorsModel
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EconomicIndicatorDataRepository(
    private val remoteDataSource: RemoteDataSource = injector.remoteDataSource,
    private val localDataSource: LocalDataSource = injector.localDataSource
) : EconomicIndicatorsRepository {

    override suspend fun getEconomicIndicators(): List<EconomicIndicatorModel> {
        val economicIndicators: List<EconomicIndicatorModel>
        val local = localDataSource.getAll()
        when {
            !local.isNullOrEmpty() -> {
                economicIndicators =
                    local.map { economicIndicatorEntity -> economicIndicatorEntity.toEconomicIndicatorModel() }
            }
            else -> {
                val economicIndicatorsResponse = remoteDataSource.getEconomicIndicators()
                economicIndicators = economicIndicatorsResponse.toEconomicIndicatorsModel()
                saveEconomicIndicators(economicIndicatorsResponse.toEconomicIndicatorsEntity())
            }
        }

        return economicIndicators
    }

    override suspend fun getEconomicIndicatorDetail(code: String): EconomicIndicatorModel {
        val localIndicatorEntity = localDataSource.findByCode(code)
        return localIndicatorEntity.toEconomicIndicatorModel()
    }

    override suspend fun saveEconomicIndicators(economicIndicators: List<EconomicIndicatorEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertAll(*economicIndicators.toTypedArray())
        }
    }
}

