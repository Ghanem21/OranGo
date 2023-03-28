package com.example.data.roomDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey val id: Int,
    val date: String,
    val receipt_number: Int,
    val time: String,
    val total_price: Int
)