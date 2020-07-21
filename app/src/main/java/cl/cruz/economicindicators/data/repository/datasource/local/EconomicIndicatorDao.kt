package cl.cruz.economicindicators.data.repository.datasource.local

import androidx.room.*
import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity

@Dao
interface EconomicIndicatorDao : LocalDataSource {
    @Query("SELECT * FROM economic_indicators")
    override suspend fun getAll(): List<EconomicIndicatorEntity>

    @Query("SELECT * FROM economic_indicators WHERE code LIKE :code LIMIT 1")
    override fun findByCode(code: String): EconomicIndicatorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertAll(vararg economicIndicatorEntity: EconomicIndicatorEntity)
}
