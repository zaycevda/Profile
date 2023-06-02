package com.example.domain.usecases

import com.example.domain.models.Phone
import com.example.domain.repository.ApiRepository

class AuthUseCase(private val repository: ApiRepository) {
    suspend fun execute(phone: Phone) = repository.auth(phone = phone)
}