package com.example.testformangofzco.viewmodels.account_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.AuthUseCase
import com.example.domain.usecases.CheckAuthUseCase

class AuthorizationViewModelFactory(
    private val authUseCase: AuthUseCase,
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AuthorizationViewModel(
            authUseCase = authUseCase,
            checkAuthUseCase = checkAuthUseCase
        ) as T
}