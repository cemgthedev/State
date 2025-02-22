package com.inovassessoria.state.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TEnterprise(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "") val name: String
)