package com.example.domain.entity.json.auth.updateProfile

import com.example.domain.entity.json.auth.logIn.User

data class CustomerData(
    val created_at: String,
    val credit_card: String?,
    val email: String,
    val email_verified_at: String?,
    val id: Int,
    val image: String,
    val limited_price: Double?,
    val password: String,
    val phone_number: String,
    val points: Int,
    val remember_token: String?,
    val updated_at: String,
    val user_name: String
)

fun CustomerData.toUser(): User {
    return User(
        createdAt = created_at,
        credit_card = credit_card,
        email = email,
        email_verified_at = email_verified_at,
        id = id,
        image = image,
        limited_price = limited_price,
        password = password,
        phone_number = phone_number,
        points = points,
        remember_token = remember_token,
        updated_at = updated_at,
        user_name = user_name
    )

}