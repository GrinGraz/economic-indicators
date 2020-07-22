package cl.cruz.economicindicators.data.repository.datasource.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cl.cruz.economicindicators.App
import cl.cruz.economicindicators.data.model.local.EconomicIndicatorEntity

@Database(
    entities = [EconomicIndicatorEntity::class],
    views = [EconomicIndicatorDetailView::class, EconomicIndicatorView::class],
    version = 1
)
abstract class EconomicIndicatorsDatabase : RoomDatabase() {
    abstract fun economicIndicatorDao(): EconomicIndicatorDao

    companion object {
        private const val DB_NAME = "economic_indicators.db"

        val instance: EconomicIndicatorsDatabase by lazy {
            Room.databaseBuilder(App.context, EconomicIndicatorsDatabase::class.java, DB_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // will create stub data
                    }
                }).build()
        }
    }
}
