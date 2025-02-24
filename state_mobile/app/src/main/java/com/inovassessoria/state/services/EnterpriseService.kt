package com.inovassessoria.state.services

import com.inovassessoria.state.models.TEnterprise
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET

@JsonClass(generateAdapter = true)
data class EnterprisesResponse(
    @field:Json(name="totalItems") val totalItems: Int,
    @field:Json(name="items") val items: List<TEnterprise>
)
// Interface do serviço de enterprises
interface EnterpriseService {
    @GET("enterprises/client")
    suspend fun search(): EnterprisesResponse
}