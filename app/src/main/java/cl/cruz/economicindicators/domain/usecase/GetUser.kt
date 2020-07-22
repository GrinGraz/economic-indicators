package cl.cruz.economicindicators.domain.usecase

import cl.cruz.economicindicators.domain.repository.LoginRepository

class GetUser(private val loginRepository: LoginRepository) {
    operator fun invoke(): String = loginRepository.getUser()
}