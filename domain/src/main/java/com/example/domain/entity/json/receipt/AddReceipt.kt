package com.example.domain.entity.json.receipt

data class AddReceipt (
    val error: Error?,
    val msg: String,
    val status: Boolean,
)

data class Error(
    val customer_id: List<String>
)