package cl.cruz.economicindicators.di

import android.content.Context
import cl.cruz.economicindicators.App
import cl.cruz.economicindicators.data.repository.EconomicIndicatorDataRepository
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsLocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsRemoteDataSource
import cl.cruz.economicindicators.data.repository.datasource.local.EconomicIndicatorsDatabase
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.remote.EconomicIndicatorsService
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicator
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicators

val injector: Injector by lazy {
    Injector()
}

class Injector {

    val applicationContext: Context by lazy {
        App.context
    }

    val database: EconomicIndicatorsDatabase by lazy {
        EconomicIndicatorsDatabase.getInstance(applicationContext)
    }

    val apiService: EconomicIndicatorsService by lazy {
        EconomicIndicatorsService.economicIndicatorsApi
    }

    val localDataSource: LocalDataSource by lazy {
        EconomicIndicatorsLocalDataSource(database)
    }

    val remoteDataSource: RemoteDataSource by lazy {
        EconomicIndicatorsRemoteDataSource(apiService)
    }

    val repository: EconomicIndicatorsRepository =
        EconomicIndicatorDataRepository(remoteDataSource, localDataSource)

    val getEconomicIndicators: GetEconomicIndicators = GetEconomicIndicators(repository)

    val getEconomicIndicator: GetEconomicIndicator = GetEconomicIndicator(repository)
}