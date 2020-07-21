package cl.cruz.economicindicators.domain.usecase

import cl.cruz.economicindicators.domain.repository.LoginRepository

class LogoutUser(private val loginRepository: LoginRepository){
    operator fun invoke() {
        loginRepository.logout()
    }
}