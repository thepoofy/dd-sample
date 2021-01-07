package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
  val city: String?,
  val country: String?,
  val id: Int?,
  val lat: Double?,
  val lng: Double?,
  @Json(name = "printable_address") val printableAddress: String?,
  val shortname: String?,
  val state: String?,
  val street: String?,
  val subpremise: String?,
  @Json(name = "zip_code") val zipCode: String?
)