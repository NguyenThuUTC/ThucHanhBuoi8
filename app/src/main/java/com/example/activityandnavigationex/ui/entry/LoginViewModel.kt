package com.example.activityandnavigationex.ui.entry

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activityandnavigationex.data.model.LoginRequest
import com.example.activityandnavigationex.data.remote.ServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {

    val service = ServiceProvider.api
    private var _uiLoginState =  MutableLiveData<ValidationResult>()
    val uiLoginState: LiveData<ValidationResult> = _uiLoginState
    fun handleLogin(email: String, password: String) {

        val emailError = checkEmail(email)

        val passwordError = checkPassword(password)
        val isValid = emailError == null && passwordError == null

        if (isValid) {
            //call api
            callLoginApi(email = email, password)

            ////
        } else {
            _uiLoginState.value =
                ValidationResult.Invalid(error = Pair(emailError, passwordError))
        }

    }

    private fun callLoginApi(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginResult = service.login(LoginRequest(username = email, password = password))
                Log.d("LoginViewModel", "Login result: ${loginResult.userId}, ${loginResult.token}")

                if (loginResult.userId != null) {
                    //login success
                    _uiLoginState.postValue(ValidationResult.Valid)
                } else {
                    _uiLoginState.postValue(
                        ValidationResult.Invalid(
                            error = Pair(
                                ErrorUiState.WrongEmailOrPassword,
                                null
                            )
                        )
                    )
                    //login fail
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", e.message ?: "Exception")
            }
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

    object WrongEmailOrPassword: ErrorUiState()
    object PasswordTooShort: ErrorUiState()
}