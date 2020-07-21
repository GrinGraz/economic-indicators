package cl.cruz.economicindicators.data.model

import cl.cruz.economicindicators.domain.model.EconomicIndicatorModel

sealed class Result<out R> {

    data class Success<out T: List<EconomicIndicatorModel>>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null
