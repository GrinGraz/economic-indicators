package cl.cruz.economicindicators.data.repository.datasource

import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import cl.cruz.economicindicators.data.repository.datasource.local.EconomicIndicatorsDatabase
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import kotlinx.coroutines.flow.Flow

class EconomicIndicatorsLocalDataSource(
    private val economicIndicatorsDatabase: EconomicIndicatorsDatabase =
        EconomicIndicatorsDatabase.instance
) : LocalDataSource {
    override fun getAll(): Flow<List<EconomicIndicatorEntity>> {
        return economicIndicatorsDatabase.economicIndicatorDao().getAll()
    }

    override fun findByCode(code: String): EconomicIndicatorEntity {
        return economicIndicatorsDatabase.economicIndicatorDao().findByCode(code)
    }

    override fun insertAll(vararg economicIndicatorEntity: EconomicIndicatorEntity) {
        economicIndicatorsDatabase.economicIndicatorDao().insertAll(*economicIndicatorEntity)
    }

    override fun delete(economicIndicatorEntity: EconomicIndicatorEntity) {
        TODO("Not yet implemented")
    }
}
