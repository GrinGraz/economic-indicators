package cl.cruz.economicindicators.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.cruz.economicindicators.data.model.Result
import cl.cruz.economicindicators.data.model.get
import cl.cruz.economicindicators.di.injector
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicator
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicators
import cl.cruz.economicindicators.domain.usecase.LogoutUser
import cl.cruz.economicindicators.ui.detail.EconomicIndicator
import cl.cruz.economicindicators.ui.main.ConnectionState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.concurrent.atomic.AtomicInteger

class EconomicIndicatorsViewModel(
    private val getEconomicIndicators: GetEconomicIndicators = injector.getEconomicIndicators,
    private val getEconomicIndicator: GetEconomicIndicator = injector.getEconomicIndicator,
    private val logoutUser: LogoutUser = injector.logoutUser
) : ViewModel() {

    private val retryCount = AtomicInteger(0)
    private val maxRetry = 10

    private val _items =
        MutableLiveData<Result<List<EconomicIndicator>>>().apply { value = Result.Loading }
    val items: LiveData<Result<List<EconomicIndicator>>> = _items

    private val _item = MutableLiveData<Result<EconomicIndicator>>()
    val item: LiveData<Result<EconomicIndicator>> = _item

    private val _connectionState = MutableLiveData<ConnectionState>()
    val connectionState: LiveData<ConnectionState> = _connectionState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnknownHostException -> checkForLocalData()
            else -> Result.Error(throwable as Exception)
        }
    }

    private fun checkForLocalData() {
        retryCount.addAndGet(1)
        viewModelScope.launch(coroutineExceptionHandler) {
            if (retryCount.compareAndSet(maxRetry, 0))
                _items.value = Result.Error(Exception())
            else {
                delay(500)
                if (getEconomicIndicators(false).get().isNotEmpty())
                    _connectionState.value = ConnectionState
                _items.value = Result.Loading
                _items.value =
                    Result.Success(
                        getEconomicIndicators(false).get().map { it.toEconomicIndicator() })
            }
        }
    }

    fun loadEconomicIndicators(forced: Boolean = false) {
        _items.value = Result.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            _items.value =
                Result.Success(getEconomicIndicators(forced).get().map { it.toEconomicIndicator() })
        }
    }


    fun loadEconomicIndicator(code: String) =
        viewModelScope.launch(coroutineExceptionHandler) {
            _item.value = Result.Success(getEconomicIndicator(code).get().toEconomicIndicator())
        }


    fun clearUser() = logoutUser()
}
