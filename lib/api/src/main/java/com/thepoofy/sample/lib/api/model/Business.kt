package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Business(
  @Json(name = "business_vertical") val businessVertical: Any?,
  val id: Int?,
  val name: String?
)