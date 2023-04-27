package com.example.domain.entity.json.auth.signUp

import com.squareup.moshi.Json

data class Error(
    val email: List<String>?,
    @Json(name = "phone_number")
    val phoneNumber: List<String>?
)