package com.inovassessoria.state.repositories

import com.inovassessoria.state.models.TEnterprise
import com.inovassessoria.state.services.EnterpriseService

class EnterpriseRepository(private val enterpriseService: EnterpriseService) {
    suspend fun search(authHeader: String): Result<TEnterprise> {
        return try {
            val response = enterpriseService.search(authHeader)

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}