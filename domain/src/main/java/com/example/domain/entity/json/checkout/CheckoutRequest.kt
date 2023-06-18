package com.example.domain.entity.json.checkout

data class CheckoutRequest(
    val customer_id: Int,
    val data: Map<String,Int>
)