package cl.cruz.economicindicators.data.repository.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity

@Database(
    entities = [EconomicIndicatorEntity::class],
    version = 2
)
abstract class EconomicIndicatorsDatabase : RoomDatabase() {
    abstract fun economicIndicatorDao(): EconomicIndicatorDao

    companion object {
        private const val DB_NAME = "economic_indicators.db"

        fun getInstance(context: Context): EconomicIndicatorsDatabase {
            return Room.databaseBuilder(context, EconomicIndicatorsDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // will create stub data
                    }
                }).build()
        }
    }
}
