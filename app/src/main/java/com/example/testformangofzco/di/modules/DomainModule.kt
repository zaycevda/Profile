package com.example.testformangofzco.di.modules

import com.example.domain.repository.ApiRepository
import com.example.domain.usecases.AuthUseCase
import com.example.domain.usecases.CheckAuthUseCase
import com.example.domain.usecases.GetRefreshTokenUseCase
import com.example.domain.usecases.GetTokenUseCase
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.RegistrationUseCase
import com.example.domain.usecases.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideAuthUseCase(repository: ApiRepository) =
        AuthUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideCheckAuthUseCase(repository: ApiRepository) =
        CheckAuthUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideGetRefreshTokenUseCase(repository: ApiRepository) =
        GetRefreshTokenUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideGetTokenUseCase(repository: ApiRepository) =
        GetTokenUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideGetUserUseCase(repository: ApiRepository) =
        GetUserUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideRegistrationUseCase(repository: ApiRepository) =
        RegistrationUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(repository: ApiRepository) =
        UpdateUserUseCase(repository = repository)
}