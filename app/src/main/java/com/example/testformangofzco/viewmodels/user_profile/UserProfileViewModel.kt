package com.example.testformangofzco.viewmodels.user_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.GetUserUseCase
import com.example.testformangofzco.viewmodels.ScreenState
import com.example.testformangofzco.viewmodels.ScreenState.ErrorScreenState
import com.example.testformangofzco.viewmodels.ScreenState.LoadingScreenState
import com.example.testformangofzco.viewmodels.ScreenState.SuccessScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _user: MutableStateFlow<ScreenState<User>> =
        MutableStateFlow(SuccessScreenState(User.emptyUser()))
    val user: StateFlow<ScreenState<User>> = _user

    fun getUser() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _user.value = ErrorScreenState(throwable)
            Log.e(TAG, "getUser: ${throwable.message}")
        }

        viewModelScope.launch(exceptionHandler) {
            _user.value = LoadingScreenState()
            val user = getUserUseCase.execute()
            Log.d(TAG, "getUser: user = $user")
            _user.value = SuccessScreenState(user)
        }
    }

    private companion object {
        private const val TAG = "UserProfileViewModel"
    }
}