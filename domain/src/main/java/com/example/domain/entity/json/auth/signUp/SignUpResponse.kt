package com.example.domain.entity.json.auth.signUp

data class SignUpResponse(
    val error: Error?,
    val msg: String,
    val status: Boolean
)