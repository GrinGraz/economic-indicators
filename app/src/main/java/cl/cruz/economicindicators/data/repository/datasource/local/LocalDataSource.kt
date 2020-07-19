package cl.cruz.economicindicators.data.repository.datasource.local

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAll(): Flow<List<EconomicIndicatorEntity>>
    fun findByCode(code: String): EconomicIndicatorEntity
    fun insertAll(vararg economicIndicatorEntity: EconomicIndicatorEntity)
    fun delete(economicIndicatorEntity: EconomicIndicatorEntity)
}