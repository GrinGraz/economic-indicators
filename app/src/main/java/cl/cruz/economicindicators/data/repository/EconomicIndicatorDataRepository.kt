package cl.cruz.economicindicators.data.repository

import cl.cruz.economicindicators.data.model.Result
import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorModel
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorsEntity
import cl.cruz.economicindicators.domain.mapper.toEconomicIndicatorsModel
import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository
import kotlinx.coroutines.*

class EconomicIndicatorDataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : EconomicIndicatorsRepository {

    override suspend fun getEconomicIndicators(forced: Boolean): Result<List<EconomicIndicatorModel>> {
        return if (forced) {
            fetchAndSaveIndicators()
        } else {
            val localIndicators = localDataSource.getAll()
            if (!localIndicators.isNullOrEmpty()) {
                Result.Success(localIndicators.map { it.toEconomicIndicatorModel() })
            } else {
                fetchAndSaveIndicators()
            }
        }
    }

    private suspend fun fetchAndSaveIndicators(): Result<List<EconomicIndicatorModel>> =
        runCatching {
            remoteDataSource.getEconomicIndicators()
        }.fold(
            onSuccess = {
                saveEconomicIndicators(it.toEconomicIndicatorsEntity())
                Result.Success(it.toEconomicIndicatorsModel())
            },
            onFailure = {
                Result.Error(it as Exception)
            })

    override suspend fun getEconomicIndicatorDetail(code: String): Result<EconomicIndicatorModel> =
        runCatching {
            withContext(Dispatchers.IO) {
                localDataSource.findByCode(code)
            }
        }.fold(
            onSuccess = {
                Result.Success(it.toEconomicIndicatorModel())
            },
            onFailure = {
                Result.Error(it as Exception)
            })


    override suspend fun saveEconomicIndicators(economicIndicators: List<EconomicIndicatorEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertAll(*economicIndicators.toTypedArray())
        }
    }
}
