package cl.cruz.economicindicators.data.repository

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.data.model.remote.EconomicIndicatorsResponse
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorModel
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorsEntity
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorsModel
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EconomicIndicatorDataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : EconomicIndicatorsRepository {

    override suspend fun getEconomicIndicators(forced: Boolean): List<EconomicIndicatorModel> {
        return if (forced) {
            fetchAndSaveIndicators()
        } else {
            val localIndicators = localDataSource.getAll()
            if (!localIndicators.isNullOrEmpty()){
                localIndicators.map { it.toEconomicIndicatorModel() }
            } else {
                fetchAndSaveIndicators()
            }
        }
    }

    private suspend fun fetchAndSaveIndicators(): List<EconomicIndicatorModel>{
        runCatching {
            remoteDataSource.getEconomicIndicators()
        }.onFailure {
            return emptyList()
        }.onSuccess {
            saveEconomicIndicators(it.toEconomicIndicatorsEntity())
            return it.toEconomicIndicatorsModel()
        }
        return emptyList()
    }

    override suspend fun getEconomicIndicatorDetail(code: String): EconomicIndicatorModel =
        withContext(Dispatchers.IO) {
            val localIndicatorEntity = localDataSource.findByCode(code)
            localIndicatorEntity.toEconomicIndicatorModel()
        }

    override suspend fun saveEconomicIndicators(economicIndicators: List<EconomicIndicatorEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertAll(*economicIndicators.toTypedArray())
        }
    }
}

