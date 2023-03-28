package com.example.domain.entity

import com.squareup.moshi.Json

data class Product(
    @Json(name = "category_id")
    val categoryId: Int,
    @Json(name = "category_name")
    val categoryName: String,
    val id: Int,
    val image: String,
    val liked: Int,
    val location: String,
    @Json(name = "offer_value")
    val offerValue: Int,
    val price: Int,
    @Json(name = "product_name")
    val productName: String,
    val quantity: Int
)