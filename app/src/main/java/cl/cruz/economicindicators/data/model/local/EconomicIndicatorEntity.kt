package cl.cruz.economicindicators.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "economic_indicators")
data class EconomicIndicatorEntity(
    @PrimaryKey @ColumnInfo val code: String = "unknown",
    @ColumnInfo val name: String?,
    @ColumnInfo val measureUnit: String?,
    @ColumnInfo val date: String?,
    @ColumnInfo val value: Double?
)
