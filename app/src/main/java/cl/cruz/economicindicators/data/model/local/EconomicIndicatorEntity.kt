package cl.cruz.economicindicators.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import cl.cruz.economicindicators.data.model.remote.Indicator

@Entity(tableName = "economic_indicators", indices = [Index(value = ["code", "value", "name"])])
data class EconomicIndicatorEntity(
    @PrimaryKey @ColumnInfo override val code: String?,
    @ColumnInfo override val name: String?,
    @ColumnInfo override val measureUnit: String?,
    @ColumnInfo override val date: String?,
    @ColumnInfo override val value: Double?
) : Indicator
