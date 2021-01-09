package com.thepoofy.sample.lib.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val token: String
)