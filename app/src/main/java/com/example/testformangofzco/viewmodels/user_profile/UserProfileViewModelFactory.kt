package com.example.testformangofzco.viewmodels.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.GetUserUseCase

class UserProfileViewModelFactory(
    private val getUserUseCase: GetUserUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        UserProfileViewModel(
            getUserUseCase = getUserUseCase
        ) as T
}