package com.example.domain.entity.receipt

data class Receipt(
    val date: String,
    val id: Int,
    val receipt_number: Int,
    val time: String,
    val total_price: Int
)