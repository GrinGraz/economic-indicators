package cl.cruz.economicindicators.data.model

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
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

//
fun <R> Result<R>.get(): R {
    if (succeeded) {
        this as Result.Success
        return data
    }else{
        this as Result.Error
        throw exception
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null
