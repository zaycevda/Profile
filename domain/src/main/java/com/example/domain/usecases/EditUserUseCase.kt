package com.example.domain.usecases

import com.example.domain.models.UserShort
import com.example.domain.repository.ApiRepository

class EditUserUseCase(private val repository: ApiRepository) {
    suspend fun execute(userShort: UserShort) = repository.editUser(userShort = userShort)
}