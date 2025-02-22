package com.inovassessoria.state.services

import com.inovassessoria.state.models.TEnterprise
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

// Interface do serviço de enterprises
interface EnterpriseService {
    @GET("enterprises/client")
    suspend fun search(@Header("Authorization") authorization : String): TEnterprise
}