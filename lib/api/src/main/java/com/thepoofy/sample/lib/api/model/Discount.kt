package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Discount(
  val amount: Amount?,
  val description: String?,
  @Json(name = "discount_type") val discountType: String?,
  @Json(name = "min_subtotal") val minSubtotal: MinSubtotal?,
  @Json(name = "source_type") val sourceType: String?,
  val text: String?
)