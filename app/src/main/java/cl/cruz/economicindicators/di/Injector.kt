package cl.cruz.economicindicators.di

import android.content.Context
import cl.cruz.economicindicators.App
import cl.cruz.economicindicators.data.repository.EconomicIndicatorDataRepository
import cl.cruz.economicindicators.data.repository.LoginDataRepository
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsLocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.EconomicIndicatorsRemoteDataSource
import cl.cruz.economicindicators.data.repository.datasource.local.EconomicIndicatorsDatabase
import cl.cruz.economicindicators.data.repository.datasource.local.LocalDataSource
import cl.cruz.economicindicators.data.repository.datasource.sharedpreferences.SharedPreferencesDataSource
import cl.cruz.economicindicators.data.repository.datasource.SharedPrefsDataSource
import cl.cruz.economicindicators.data.repository.datasource.remote.EconomicIndicatorsService
import cl.cruz.economicindicators.data.repository.datasource.remote.RemoteDataSource
import cl.cruz.economicindicators.domain.repository.EconomicIndicatorsRepository
import cl.cruz.economicindicators.domain.repository.LoginRepository
import cl.cruz.economicindicators.domain.usecase.*

val injector: Injector by lazy {
    Injector()
}

class Injector {

    private val applicationContext: Context by lazy {
        App.context
    }

    private val database: EconomicIndicatorsDatabase by lazy {
        EconomicIndicatorsDatabase.getInstance(applicationContext)
    }

    private val apiService: EconomicIndicatorsService by lazy {
        EconomicIndicatorsService.economicIndicatorsApi
    }

    private val sharedPrefs: SharedPreferencesDataSource by lazy {
        SharedPrefsDataSource(applicationContext)
    }

    private val localDataSource: LocalDataSource by lazy {
        EconomicIndicatorsLocalDataSource(database)
    }

    private val remoteDataSource: RemoteDataSource by lazy {
        EconomicIndicatorsRemoteDataSource(apiService)
    }

    private val repository: EconomicIndicatorsRepository =
        EconomicIndicatorDataRepository(remoteDataSource, localDataSource)

    private val loginRepository: LoginRepository = LoginDataRepository(sharedPrefs)

    val loginUser: LoginUser = LoginUser(loginRepository)

    val logoutUser: LogoutUser = LogoutUser(loginRepository)

    val getUser: GetUser = GetUser(loginRepository)

    val getEconomicIndicators: GetEconomicIndicators = GetEconomicIndicators(repository)

    val getEconomicIndicator: GetEconomicIndicator = GetEconomicIndicator(repository)
}
