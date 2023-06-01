package com.example.domain.usecases

import com.example.domain.models.RefreshToken
import com.example.domain.repository.ApiRepository

class GetRefreshTokenUseCase(private val repository: ApiRepository) {
    suspend fun execute(refreshToken: RefreshToken) = repository.getRefreshToken(refreshToken = refreshToken)
}