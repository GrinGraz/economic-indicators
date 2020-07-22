package cl.cruz.economicindicators.presentation

import cl.cruz.economicindicators.ui.login.LoggedInUserView

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)