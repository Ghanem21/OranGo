package com.example.domain.entity.receipt

data class AllReceiptResponse(
    val msg: String,
    val receipts: List<Receipt>,
    val status: Boolean
)