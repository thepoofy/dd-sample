package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeliveryFeeDetails(
  val discount: Discount?,
  @Json(name = "final_fee") val finalFee: FinalFee?,
  @Json(name = "original_fee") val originalFee: OriginalFee?,
  @Json(name = "surge_fee") val surgeFee: SurgeFee?
)