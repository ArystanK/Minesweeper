package kz.arctan.minesweaper.auth.domain.util

sealed class LoginUiEvent {
    data class Navigate(val route: String) : LoginUiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ) : LoginUiEvent()

    object LoginResultLoading : LoginUiEvent()

    object LoginResultInitial : LoginUiEvent()
}
