package cl.cruz.economicindicators.data.repository.datasource.sharedpreferences

interface SharedPreferencesDataSource {
    fun saveUser(username: String, password: String)
    fun getUser(): Pair<String, String>
    fun clearUser()
}