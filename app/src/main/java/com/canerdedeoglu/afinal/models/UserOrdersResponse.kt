package com.canerdedeoglu.afinal.models

import com.google.gson.annotations.SerializedName

data class UserOrdersResponse(
    @SerializedName("carts") val carts: List<Order>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)
data class Order(
    val id: Int,
    val products: List<Product>,
    val total: Double,
    val discountedTotal: Double,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)


