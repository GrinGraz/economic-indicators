package cl.cruz.economicindicators.di

import android.app.Application
import android.content.Context
import cl.cruz.economicindicators.data.repository.EconomicIndicatorDataRepository
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsLocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsRemoteDataSource
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository

val injector: Injector by lazy {
    Injector()
}

class Injector {
    val repository: EconomicIndicatorsRepository by lazy {
        EconomicIndicatorDataRepository()
    }

    val localDataSource: LocalDataSource by lazy {
        EconomicIndicatorsLocalDataSource()
    }

    val remoteDataSource: RemoteDataSource by lazy {
        EconomicIndicatorsRemoteDataSource()
    }

    val applicationContext: Context by lazy {
        Application().applicationContext
    }
}