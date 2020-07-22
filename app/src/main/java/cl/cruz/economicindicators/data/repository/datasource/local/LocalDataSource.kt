package cl.cruz.economicindicators.data.repository.datasource.local

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity

interface LocalDataSource {
    suspend fun getAll(): List<EconomicIndicatorEntity>
    fun findByCode(code: String): EconomicIndicatorEntity
    fun insertAll(vararg economicIndicatorEntity: EconomicIndicatorEntity)
    fun delete(economicIndicatorEntity: EconomicIndicatorEntity)
}