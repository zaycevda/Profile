package com.example.domain.usecases

import com.example.domain.repository.ApiRepository

class GetTokenUseCase(private val repository: ApiRepository) {
    suspend fun execute() = repository.getToken()
}