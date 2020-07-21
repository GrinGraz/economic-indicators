package cl.cruz.economicindicators.domain.usecase

import cl.cruz.economicindicators.domain.repository.LoginRepository

class LoginUser(private val loginRepository: LoginRepository){
    operator fun invoke(username: String, password: String): Boolean {
        return loginRepository.login(username, password)
    }
}