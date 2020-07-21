package cl.cruz.economicindicators.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.cruz.economicindicators.di.injector
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicator
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicators
import cl.cruz.economicindicators.domain.usecase.LogoutUser
import cl.cruz.economicindicators.ui.detail.EconomicIndicator
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownServiceException

class EconomicIndicatorsViewModel(
    private val getEconomicIndicators: GetEconomicIndicators = injector.getEconomicIndicators,
    private val getEconomicIndicator: GetEconomicIndicator = injector.getEconomicIndicator,
    private val logoutUser: LogoutUser = injector.logoutUser
) : ViewModel() {

    val items = MutableLiveData<List<EconomicIndicator>>()
    val item = MutableLiveData<EconomicIndicator>()

    fun loadEconomicIndicators(forced: Boolean = false) {
        viewModelScope.launch {
            items.value = getEconomicIndicators(forced).map { it.toEconomicIndicator() }
        }
    }

    fun loadEconomicIndicator(code: String) {
        viewModelScope.launch {
            item.value = getEconomicIndicator(code).toEconomicIndicator()
        }
    }

    fun clearUser(){
        logoutUser()
    }

    override fun onCleared() {
        super.onCleared()
        item.value = null
    }
}