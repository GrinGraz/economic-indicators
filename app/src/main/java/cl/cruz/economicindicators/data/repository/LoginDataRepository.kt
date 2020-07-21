package cl.cruz.economicindicators.data.repository

import cl.cruz.economicindicators.data.repository.datasource.sharedpreferences.SharedPreferencesDataSource
import cl.cruz.economicindicators.domain.repository.LoginRepository

class LoginDataRepository(private val sharedPreferences: SharedPreferencesDataSource): LoginRepository {

    override fun login(username: String, password: String): Boolean {
        sharedPreferences.saveUser(username, password)
        //val user = sharedPreferences.getUser()
        //user.first == username && user.second == password
        return true

    }

    override fun logout() {
        sharedPreferences.clearUser()
    }

    override fun getUser(): String {
        return sharedPreferences.getUser().first
    }
}