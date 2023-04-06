package com.example.data.roomDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val image: String,
    val liked: Int,
    val location: String,
    val offerValue: Int,
    val price: Int,
    val productName: String,
    val quantity: Int,
    val sold_units: Int
)