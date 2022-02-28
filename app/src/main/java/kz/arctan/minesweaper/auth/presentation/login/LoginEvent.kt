package kz.arctan.minesweaper.auth.presentation.login

sealed class LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent()
    object SignUp : LoginEvent()
    data class EmailChange(val newEmail: String) : LoginEvent()
    data class PasswordChange(val newPassword: String) : LoginEvent()
}
