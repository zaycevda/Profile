package com.example.testformangofzco.di.modules

import android.content.Context
import com.example.domain.usecases.AuthUseCase
import com.example.domain.usecases.CheckAuthUseCase
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.RegistrationUseCase
import com.example.domain.usecases.EditUserUseCase
import com.example.testformangofzco.viewmodels.account_creation.AuthorizationViewModelFactory
import com.example.testformangofzco.viewmodels.account_creation.RegistrationViewModelFactory
import com.example.testformangofzco.viewmodels.user_profile.EditUserProfileViewModelFactory
import com.example.testformangofzco.viewmodels.user_profile.UserProfileViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideAuthorizationViewModelFactory(
        authUseCase: AuthUseCase,
        checkAuthUseCase: CheckAuthUseCase
    ) = AuthorizationViewModelFactory(
        authUseCase = authUseCase,
        checkAuthUseCase = checkAuthUseCase
    )

    @Provides
    @Singleton
    fun provideRegistrationViewModelFactory(
        registrationUseCase: RegistrationUseCase
    ) = RegistrationViewModelFactory(
        registrationUseCase = registrationUseCase
    )

    @Provides
    @Singleton
    fun provideUserProfileViewModelFactory(
        getUserUseCase: GetUserUseCase
    ) = UserProfileViewModelFactory(
        getUserUseCase = getUserUseCase
    )

    @Provides
    @Singleton
    fun provideEditUserProfileViewModelFactory(
        editUserUseCase: EditUserUseCase
    ) = EditUserProfileViewModelFactory(
        editUserUseCase = editUserUseCase
    )
}