package com.example.testformangofzco.viewmodels.user_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Avatars
import com.example.domain.models.UserShort
import com.example.domain.usecases.EditUserUseCase
import com.example.testformangofzco.viewmodels.ScreenState
import com.example.testformangofzco.viewmodels.ScreenState.ErrorScreenState
import com.example.testformangofzco.viewmodels.ScreenState.LoadingScreenState
import com.example.testformangofzco.viewmodels.ScreenState.SuccessScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditUserProfileViewModel(
    private val editUserUseCase: EditUserUseCase
): ViewModel() {

    private val _avatars: MutableStateFlow<ScreenState<Avatars?>> =
        MutableStateFlow(SuccessScreenState(Avatars.emptyAvatars()))
    val avatars: StateFlow<ScreenState<Avatars?>> = _avatars

    fun editUser(userShort: UserShort) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "editUser: ${throwable.message}")
            _avatars.value = ErrorScreenState(throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _avatars.value = LoadingScreenState()
            val avatars = editUserUseCase.execute(userShort = userShort)
            Log.d(TAG, "editUser: avatars = $avatars")
            _avatars.value = SuccessScreenState(avatars)
        }
    }

    private companion object {
        private const val TAG = "EditUserProfileViewModel"
    }
}