package com.example.domain.entity.auth.signUp

data class Error(
    val email: List<String>?,
    val phone_number: List<String>?
)