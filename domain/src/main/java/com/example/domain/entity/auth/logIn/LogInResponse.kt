package com.example.domain.entity.auth.logIn

import com.squareup.moshi.Json

data class LogInResponse(
    @Json(name = "customer data")
    val customerData: CustomerData,
    val msg: String,
    val status: Boolean,
    val error: String?
)