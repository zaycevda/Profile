package com.example.testformangofzco.viewmodels.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.EditUserUseCase

class EditUserProfileViewModelFactory(
    private val editUserUseCase: EditUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        EditUserProfileViewModel(
            editUserUseCase = editUserUseCase
        ) as T
}