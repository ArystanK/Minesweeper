package kz.arctan.minesweaper.auth.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.arctan.minesweaper.auth.domain.usecases.LoginUseCase
import kz.arctan.minesweaper.auth.domain.util.Email
import kz.arctan.minesweaper.common.domain.model.ActionResult
import kz.arctan.minesweaper.common.domain.util.Routes
import kz.arctan.minesweaper.auth.domain.util.LoginUiEvent
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login(event.email, event.password)
            }
            LoginEvent.SignUp -> {
                sendUiEvent(LoginUiEvent.Navigate(Routes.REGISTER_SCREEN))
            }
            is LoginEvent.EmailChange -> {
                Email(event.newEmail).validate()
                email.value = event.newEmail
            }
            is LoginEvent.PasswordChange -> {
                password.value = event.newPassword
            }
        }
    }

    private fun sendUiEvent(uiEvent: LoginUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect {
                when (it) {
                    is ActionResult.Error -> {
                        sendUiEvent(LoginUiEvent.ShowSnackBar(it.message))
                    }
                    ActionResult.Loading -> {
                        sendUiEvent(LoginUiEvent.LoginResultLoading)
                    }
                    ActionResult.Success -> {
                        sendUiEvent(LoginUiEvent.Navigate(Routes.CHOOSE_GAME_MODE_SCREEN))
                    }
                }
            }
        }
    }
}