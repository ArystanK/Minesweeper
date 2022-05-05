package kz.arctan.minesweaper.auth.domain.usecases

import kotlinx.coroutines.flow.flow
import kz.arctan.minesweaper.auth.data.EmailAlreadyExistsException
import kz.arctan.minesweaper.auth.data.UnableToRegisterException
import kz.arctan.minesweaper.auth.data.UnableToSaveToDatabaseException
import kz.arctan.minesweaper.auth.domain.AuthRepository
import kz.arctan.minesweaper.auth.domain.util.Email
import kz.arctan.minesweaper.auth.domain.util.Password
import kz.arctan.minesweaper.common.domain.model.ActionResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String,
        firstName: String,
        lastName: String
    ) = flow {
        emit(ActionResult.Loading)
        try {
            if (!Email(email).validate().isValid) emit(ActionResult.Error("Invalid email"))
            if (!Password(password to repeatPassword).validate().isValid) emit(ActionResult.Error("Invalid password"))
            authRepository.register(email, password, firstName, lastName)
            emit(ActionResult.Success)
        } catch (e: EmailAlreadyExistsException) {
            emit(ActionResult.Error("Email already exists"))
        } catch (e: UnableToSaveToDatabaseException) {
            emit(ActionResult.Error("Cannot save to database"))
        } catch (e: UnableToRegisterException) {
            emit(ActionResult.Error("Cannot register"))
        }
    }
}
