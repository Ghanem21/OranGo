package com.example.domain.entity.json.auth.logIn

data class LogInResponse(
    val customerData: CustomerData?,
    val msg: String,
    val status: Boolean,
    val error: String?
)