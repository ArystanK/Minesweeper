package kz.arctan.minesweaper.auth.domain.util

sealed class ValidationResult(val isValid: Boolean) {
    class PasswordValidationResult(
        val containsUpperCaseLetters: Boolean,
        val containsNumbers: Boolean,
        val containsLowerCaseLetters: Boolean,
        val passwordsTheSame: Boolean,
    ) : ValidationResult(containsLowerCaseLetters && containsNumbers && containsUpperCaseLetters && passwordsTheSame)

    class EmailValidationResult(isValid: Boolean) : ValidationResult(isValid)
}


// pair is password and repeat password
class Password(private val content: Pair<String, String>) {
    fun validate(): ValidationResult {
        return ValidationResult.PasswordValidationResult(
            passwordsTheSame = content.first == content.second,
            containsLowerCaseLetters = content.first.find { it.isLowerCase() } != null,
            containsNumbers = content.first.find { it.isDigit() } != null,
            containsUpperCaseLetters = content.first.find { it.isUpperCase() } != null
        )
    }
}

class Email(private val content: String) {
    fun validate(): ValidationResult {
        return ValidationResult.EmailValidationResult(
            android.util.Patterns.EMAIL_ADDRESS.matcher(
                content
            ).matches()
        )
    }
}