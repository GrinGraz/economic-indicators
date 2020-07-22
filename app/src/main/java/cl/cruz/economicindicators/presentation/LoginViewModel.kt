package cl.cruz.economicindicators.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.cruz.economicindicators.R
import cl.cruz.economicindicators.di.injector
import cl.cruz.economicindicators.domain.usecase.GetUser
import cl.cruz.economicindicators.domain.usecase.LoginUser
import cl.cruz.economicindicators.ui.login.LoggedInUserView


class LoginViewModel(
    private val loginUser: LoginUser = injector.loginUser,
    private val getUser: GetUser = injector.getUser
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        val result = loginUser(username, password)

        if (result) {
            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = username))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun getUserData(): String = getUser()

    private fun isUserNameValid(username: String): Boolean = username.isNotBlank()

    private fun isPasswordValid(password: String): Boolean = password.length > 5

}