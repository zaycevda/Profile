package com.example.domain.usecases

import com.example.domain.models.Phone
import com.example.domain.repository.ApiRepository

class CheckAuthUseCase(private val repository: ApiRepository) {
    suspend fun execute(phone: Phone) = repository.checkAuth(phone = phone)
}