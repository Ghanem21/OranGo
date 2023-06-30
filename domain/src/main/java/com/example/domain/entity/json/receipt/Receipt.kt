package com.example.domain.entity.json.receipt

data class Receipt(
    val date: String,
    val id: Int,
    val receipt_number: Int,
    val user_name: String,
    val total_price: Int
)