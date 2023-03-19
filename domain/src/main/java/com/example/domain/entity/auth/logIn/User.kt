package com.example.domain.entity.auth.logIn

import com.squareup.moshi.Json

data class User(
    @Json(name = "created_at")
    val createdAt: String?,
    val credit_card: String?,
    val email: String,
    val email_verified_at: String?,
    val id: Int,
    val image: String?,
    val limited_price: Double?,
    val password: String,
    val phone_number: String,
    val points: Int,
    val remember_token: String?,
    val updated_at: String?,
    val user_name: String
)