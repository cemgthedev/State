package com.inovassessoria.state.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TSetting(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "document") val document: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "phoneWsp") val phoneWsp: String?,
    @Json(name = "avatarUrl") val avatarUrl: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "status") val status: String?,
    @Json(name = "isOpen") val isOpen: Boolean,
    @Json(name = "representativeId") val representativeId: Int?,
    @Json(name = "userId") val userId: Int,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    @Json(name = "deleted_at") val deletedAt: String?,
    @Json(name = "representative") val representative: TUser?
)