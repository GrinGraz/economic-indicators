package cl.cruz.economicindicators.domain.repository

interface LoginRepository {
    fun login(username: String, password: String): Boolean
    fun logout()
    fun getUser(): String
}
