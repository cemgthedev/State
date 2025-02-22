package com.inovassessoria.state.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TUserLogged(
    @field:Json(name="id") val id: Int,
    @field:Json(name="name") val name: String,
    @field:Json(name="email") val email: String,
    @field:Json(name="document") val document: String? = null,
    @field:Json(name="password") val password: String? = null,
    @field:Json(name="avatarUrl") val avatarUrl: String? = null,
    @field:Json(name="phone") val phone: String? = null,

    @field:Json(name="permissionGroupId") val permissionGroupId: Int? = null,
    @field:Json(name="permissionGroup") val permissionGroup: PermissionGroup? = null,
    @field:Json(name="role") val role: String? = null,

    @field:Json(name="uf") val uf: String? = null,
    @field:Json(name="city") val city: String? = null,
    @field:Json(name="street") val street: String? = null,
    @field:Json(name="neighborhood") val neighborhood: String? = null,
    @field:Json(name="number") val number: String? = null,
    @field:Json(name="complement") val complement: String? = null,
    @field:Json(name="cep") val cep: String? = null,
    @field:Json(name="linkMap") val linkMap: String? = null,
    @field:Json(name="createdAt") val createdAt: String? = null,
    @field:Json(name="updatedAt") val updatedAt: String? = null,
    @field:Json(name="deletedAt") val deletedAt: String? = null,

    @field:Json(name="exp") val exp: Int? = null,
    @field:Json(name="iat") val iat: Int? = null
)