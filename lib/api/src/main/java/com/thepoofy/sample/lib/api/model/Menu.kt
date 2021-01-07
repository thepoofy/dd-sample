package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Menu(
  val id: Int?,
  @Json(name = "is_business_enabled") val isBusinessEnabled: Any?,
  @Json(name = "is_catering") val isCatering: Boolean?,
  @Json(name = "menu_version") val menuVersion: Int?,
  val name: String?,
  @Json(name = "open_hours") val openHours: List<List<OpenHour>>?,
  val status: String?,
  @Json(name = "status_type") val statusType: String?,
  val subtitle: String?
)