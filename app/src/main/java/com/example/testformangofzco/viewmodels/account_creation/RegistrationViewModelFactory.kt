package com.example.testformangofzco.viewmodels.account_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.RegistrationUseCase

class RegistrationViewModelFactory(
    private val registrationUseCase: RegistrationUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        RegistrationViewModel(
            registrationUseCase = registrationUseCase
        ) as T
}