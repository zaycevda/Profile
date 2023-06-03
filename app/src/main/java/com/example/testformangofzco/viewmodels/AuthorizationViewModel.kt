package com.example.testformangofzco.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Auth
import com.example.domain.models.CheckAuth
import com.example.domain.models.Phone
import com.example.domain.models.Success
import com.example.domain.usecases.AuthUseCase
import com.example.domain.usecases.CheckAuthUseCase
import com.example.testformangofzco.presentation.AuthorizationFragment
import com.example.testformangofzco.viewmodels.ScreenState.ErrorScreenState
import com.example.testformangofzco.viewmodels.ScreenState.LoadingScreenState
import com.example.testformangofzco.viewmodels.ScreenState.SuccessScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val authUseCase: AuthUseCase,
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {

    private val _success: MutableStateFlow<ScreenState<Success>> =
        MutableStateFlow(SuccessScreenState(Success(isSuccess = false)))
    val success: StateFlow<ScreenState<Success>> = _success

    private val _auth: MutableStateFlow<ScreenState<Auth>> =
        MutableStateFlow(SuccessScreenState(Auth(isUserExists = false)))
    val auth: StateFlow<ScreenState<Auth>> = _auth

    fun auth(phone: Phone) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "auth: ${throwable.message}")
            _success.value = ErrorScreenState(throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _success.value = LoadingScreenState()
            val success =  authUseCase.execute(phone = phone)
            Log.d(TAG, "auth: success = $success")
            _success.value = SuccessScreenState(success)
        }
    }

    fun checkAuth(checkAuth: CheckAuth) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "checkAuth: throwable = $throwable")
            _auth.value = ErrorScreenState(throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _auth.value = LoadingScreenState()
            val auth = checkAuthUseCase.execute(checkAuth = checkAuth)
            Log.d(TAG, "auth: auth = $auth")
            _auth.value = SuccessScreenState(auth)
        }
    }

    private companion object {
        private const val TAG = "AuthorizationViewModel"
    }
}