package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SurgeFee(
  @Json(name = "display_string") val displayString: String?,
  @Json(name = "unit_amount") val unitAmount: Int?
)