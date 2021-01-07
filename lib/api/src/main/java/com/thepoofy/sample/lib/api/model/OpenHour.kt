package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenHour(
  val hour: Int?,
  val minute: Int?
)