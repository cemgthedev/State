package com.inovassessoria.state.repositories

import com.inovassessoria.state.services.EnterpriseService
import com.inovassessoria.state.services.EnterprisesResponse

class EnterpriseRepository(private val enterpriseService: EnterpriseService) {
    suspend fun search(): Result<EnterprisesResponse> {
        return try {
            val response = enterpriseService.search()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}