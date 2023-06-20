package com.example.data.roomDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.json.receipt.Receipt

@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey val id: Int,
    val date: String,
    val receipt_number: Int,
    val user_name: String,
    val total_price: Int
)

fun List<ReceiptEntity>.asJsonModel(): List<Receipt> {
    return map {
        Receipt(
            id = it.id,
            date = it.date,
            receipt_number = it.receipt_number,
            user_name = it.user_name,
            total_price = it.total_price
        )
    }
}

fun List<Receipt>.asDatabaseModel(): List<ReceiptEntity> {
    return map {
        ReceiptEntity(
            id = it.id,
            date= it.date,
            receipt_number = it.receipt_number,
            user_name = it.user_name,
            total_price = it.total_price,
        )
    }
}