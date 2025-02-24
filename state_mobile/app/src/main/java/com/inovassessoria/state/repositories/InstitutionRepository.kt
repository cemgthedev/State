package com.inovassessoria.state.repositories

import com.inovassessoria.state.services.InstitutionResponse
import com.inovassessoria.state.services.InstitutionService

class InstitutionRepository(private val institutionService: InstitutionService) {
    suspend fun search(authorization : String): Result<InstitutionResponse> {
        return try {
            val response = institutionService.search(authorization)
            println(response)
            Result.success(response)
        } catch (e: Exception) {
            println(e)
            Result.failure(e)
        }
    }
}