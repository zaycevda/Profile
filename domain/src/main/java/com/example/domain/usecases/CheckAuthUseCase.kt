package com.example.domain.usecases

import com.example.domain.models.CheckAuth
import com.example.domain.repository.ApiRepository

class CheckAuthUseCase(private val repository: ApiRepository) {
    suspend fun execute(checkAuth: CheckAuth) = repository.checkAuth(checkAuth = checkAuth)
}