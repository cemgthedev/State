package com.inovassessoria.state.services

import com.inovassessoria.state.models.TUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.POST

@JsonClass(generateAdapter = true)
data class UserRegisterResponse(
    @field:Json(name="data") val data: TUser,
)

interface UserRegisterService {
    @POST("/clients/create")
    suspend fun userRegister(@Body user: TUser): UserRegisterResponse
}