package com.example.domain.entity.json.auth.updateProfile

data class UpdateProfileResponse(
    val customerData: CustomerData?,
    val msg: String,
    val status: Boolean
)