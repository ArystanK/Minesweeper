package kz.arctan.minesweaper.auth.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.arctan.minesweaper.auth.domain.AuthRepository
import kz.arctan.minesweaper.common.domain.model.ActionResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, password: String): Flow<ActionResult> = flow {
        emit(ActionResult.Loading)
        if (authRepository.login(email, password)) emit(ActionResult.Success)
        else emit(ActionResult.Error("Invalid credentials or bad internet connection"))
    }
}