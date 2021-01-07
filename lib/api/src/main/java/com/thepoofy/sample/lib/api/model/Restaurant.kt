package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class
Restaurant(
  @Json(name = "cover_img_url") val coverImgUrl: String?,
  @Json(name = "delivery_fee") val deliveryFee: Int?,
  val description: String?,
  val id: Int?,
  val name: String?,
  val status: String?
)