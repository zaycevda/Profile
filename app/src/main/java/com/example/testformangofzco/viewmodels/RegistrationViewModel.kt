package com.example.testformangofzco.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Tokens
import com.example.domain.models.UserMini
import com.example.domain.usecases.RegistrationUseCase
import com.example.testformangofzco.viewmodels.ScreenState.ErrorScreenState
import com.example.testformangofzco.viewmodels.ScreenState.LoadingScreenState
import com.example.testformangofzco.viewmodels.ScreenState.SuccessScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _tokens: MutableStateFlow<ScreenState<Tokens>> =
        MutableStateFlow(SuccessScreenState(Tokens()))
    val tokens: StateFlow<ScreenState<Tokens>> = _tokens

    fun registration(userMini: UserMini) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "checkAuth: throwable = $throwable")
            _tokens.value = ErrorScreenState(throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _tokens.value = LoadingScreenState()
            val tokens = registrationUseCase.execute(userMini = userMini)
            Log.d(TAG, "checkAuth: tokens = $tokens")
            _tokens.value = SuccessScreenState(tokens)
        }
    }

    private companion object {
        private const val TAG = "RegistrationViewModel"
    }
}

