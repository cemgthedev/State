package com.inovassessoria.state.services

import com.inovassessoria.state.models.TUserLogged
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.POST

// Data class para representar as credenciais de login
@JsonClass(generateAdapter = true)
data class Credential(
    @field:Json(name="email") val email: String,
    @field:Json(name="password") val password: String
)

// Data class para representar a resposta da autenticação
@JsonClass(generateAdapter = true)
data class AuthenticationResponse(
    @field:Json(name="access_token") val accessToken: String,
    @field:Json(name="data") val data: TUserLogged
)

// Interface do serviço de autenticação
interface AuthenticationService {
    @POST("/client/auth/login")
    suspend fun login(@Body credential: Credential): AuthenticationResponse
}
