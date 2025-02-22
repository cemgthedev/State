package com.inovassessoria.state.repositories

import com.inovassessoria.state.models.TUser
import com.inovassessoria.state.services.UserRegisterResponse
import com.inovassessoria.state.services.UserRegisterService

class UserRepository(private val userRegisterService: UserRegisterService) {
    suspend fun userRegister(user: TUser): Result<UserRegisterResponse> {
        return try {
            val response = userRegisterService.userRegister(user)

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}