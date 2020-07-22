package cl.cruz.economicindicators.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.cruz.economicindicators.di.injector
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicator
import cl.cruz.economicindicators.domain.usecase.GetEconomicIndicators
import kotlinx.coroutines.launch

class EconomicIndicatorsViewModel(
    private val getEconomicIndicators: GetEconomicIndicators = injector.getEconomicIndicators,
    private val getEconomicIndicator: GetEconomicIndicator = injector.getEconomicIndicator
) :
    ViewModel() {

    val items = MutableLiveData<List<EconomicIndicator>>().apply { listOf<EconomicIndicator>() }
    val item = MutableLiveData<EconomicIndicator>().apply { EconomicIndicator("", "", 0.0) }

    fun loadEconomicIndicators() {
        viewModelScope.launch {
            items.value = getEconomicIndicators().map { it.toEconomicIndicator() }
        }
    }

    fun loadEconomicIndicator(code: String) {
        viewModelScope.launch {
            item.value = getEconomicIndicator(code).toEconomicIndicator()
        }
    }
}