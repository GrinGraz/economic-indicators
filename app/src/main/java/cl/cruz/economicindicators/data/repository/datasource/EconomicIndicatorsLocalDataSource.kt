package cl.cruz.economicindicators.data.repository.datasource

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.data.repository.datasource.local.EconomicIndicatorsDatabase
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource

class EconomicIndicatorsLocalDataSource(
    private val economicIndicatorsDatabase: EconomicIndicatorsDatabase
) : LocalDataSource {
    override suspend fun getAll(): List<EconomicIndicatorEntity> {
        return economicIndicatorsDatabase.economicIndicatorDao().getAll()
    }

    override fun findByCode(code: String): EconomicIndicatorEntity {
        return economicIndicatorsDatabase.economicIndicatorDao().findByCode(code)
    }

    override fun insertAll(vararg economicIndicatorEntity: EconomicIndicatorEntity) {
        economicIndicatorsDatabase.economicIndicatorDao().insertAll(*economicIndicatorEntity)
    }
}
