package com.example.domain.usecases

import com.example.domain.models.PhoneShort
import com.example.domain.repository.ApiRepository

class AuthUseCase(private val repository: ApiRepository) {
    suspend fun execute(phoneShort: PhoneShort) = repository.auth(phoneShort = phoneShort)
}