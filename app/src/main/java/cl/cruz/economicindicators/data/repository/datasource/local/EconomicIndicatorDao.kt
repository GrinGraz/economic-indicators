package cl.cruz.economicindicators.data.repository.datasource.local

import androidx.room.*
import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EconomicIndicatorDao: LocalDataSource{
    @Query("SELECT * FROM economic_indicators")
    override fun getAll(): Flow<List<EconomicIndicatorEntity>>

    @Query("SELECT * FROM economic_indicators WHERE code LIKE :code LIMIT 1")
    override fun findByCode(code: String, last: String): EconomicIndicatorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertAll(vararg economicIndicatorEntity: EconomicIndicatorEntity)

    @Delete
    override fun delete(economicIndicatorEntity: EconomicIndicatorEntity)
}
