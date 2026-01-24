package com.example.activityandnavigationex.ui.entry

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private var _uiLoginState =  MutableLiveData<ValidationResult>()
    val uiLoginState: LiveData<ValidationResult> = _uiLoginState
    fun handleLogin(email: String, password: String) {
        val emailError = checkEmail(email)

        val passwordError = checkPassword(password)
        val isValid = emailError == null && passwordError == null

        _uiLoginState.value = if (isValid) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(error = Pair(emailError, passwordError))
        }
    }
    private fun checkPassword(password: String): ErrorUiState? {
        return if (password.isEmpty()) {
            ErrorUiState.PasswordEmpty
        } else if (password.length < 8) {
            ErrorUiState.PasswordTooShort
        } else null
    }

    private fun checkEmail(email: String): ErrorUiState? {
        //check email valid?
        return if (email.isEmpty()) {
            ErrorUiState.EmailEmpty
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ErrorUiState.WrongEmailFormat
        } else null
    }
}

data class LoginUiState(
    val emailError: String?,
    val passwordError: String?,
    val isValid: Boolean = false
)

sealed class ValidationResult {
    object Valid: ValidationResult()
    data class Invalid(val error: Pair<ErrorUiState?, ErrorUiState?>): ValidationResult()
}

sealed class ErrorUiState {
    object EmailEmpty: ErrorUiState()
    object PasswordEmpty: ErrorUiState()
    object WrongEmailFormat: ErrorUiState()
    object PasswordTooShort: ErrorUiState()
}