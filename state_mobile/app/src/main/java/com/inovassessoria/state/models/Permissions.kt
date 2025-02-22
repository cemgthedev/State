package com.inovassessoria.state.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PermissionType(
    @field:Json(name="name") val name: String,
    @field:Json(name="permission") val permission: String
)

@JsonClass(generateAdapter = true)
data class PermissionGroup(
    @field:Json(name="id") val id: Int,
    @field:Json(name="name") val name: String,
    @field:Json(name="permissions") val permissions: List<PermissionType>
)