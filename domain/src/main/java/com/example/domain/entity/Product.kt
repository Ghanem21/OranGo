package com.example.domain.entity

import com.squareup.moshi.Json

data class Product(
    @Json(name = "category_id")
    val categoryId: Int,
    val category_name: String,
    val id: Int,
    val image: String,
    val liked: Int,
    val location: String,
    val offer_value: Int,
    val price: Int,
    val product_name: String,
    val quantity: Int
)