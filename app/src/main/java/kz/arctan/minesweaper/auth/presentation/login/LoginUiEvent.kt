package kz.arctan.minesweaper.auth.presentation.login

sealed class LoginUiEvent {
    data class Navigate(val route: String) : LoginUiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ) : LoginUiEvent()

    object LoginResultLoading : LoginUiEvent()
}
