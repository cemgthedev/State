package com.inovassessoria.state.repositories

import com.inovassessoria.state.services.AuthenticationResponse
import com.inovassessoria.state.services.AuthenticationService
import com.inovassessoria.state.services.Credential

class AuthRepository(private val authService: AuthenticationService) {
    private var authToken: String? = null

    suspend fun login(credential: Credential): Result<AuthenticationResponse> {
        return try {
            val response = authService.login(credential)
            authToken = response.accessToken
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getToken(): String? {
        return authToken
    }
}