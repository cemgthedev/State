package com.inovassessoria.state.services

import com.inovassessoria.state.models.TInstitution
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Header

@JsonClass(generateAdapter = true)
data class InstitutionResponse(
    @field:Json(name="totalItems") val totalItems: Int,
    @field:Json(name="items") val items: List<TInstitution>
)

// Interface do serviço de institution
interface InstitutionService {
    @GET("institutions/client")
    suspend fun search(@Header("Authorization") authorization : String): InstitutionResponse
}