package com.example.testformangofzco.di.modules

import android.content.Context
import com.example.domain.usecases.AuthUseCase
import com.example.domain.usecases.CheckAuthUseCase
import com.example.testformangofzco.viewmodels.AuthorizationViewModelFactory
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
}