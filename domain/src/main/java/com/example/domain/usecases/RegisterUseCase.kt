package com.example.domain.usecases

import com.example.domain.models.UserMini
import com.example.domain.repository.ApiRepository

class RegisterUseCase(private val repository: ApiRepository) {
    suspend fun execute(userMini: UserMini) = repository.register(userMini = userMini)
}