package com.thepoofy.sample.lib.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MerchantPromotion(
  @Json(name = "category_id") val categoryId: Int?,
  @Json(name = "delivery_fee") val deliveryFee: Int?,
  val description: String?,
  val id: Int?,
  @Json(name = "minimum_order_cart_subtotal") val minimumOrderCartSubtotal: Int?,
  @Json(name = "new_store_customers_only") val newStoreCustomersOnly: Boolean?,
  @Json(name = "sort_order") val sortOrder: Int?,
  val title: String?
)