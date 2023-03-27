package com.example.data.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class ProductsEntity (
    val categoryId: Int,
    val category_name: String,
    @PrimaryKey val id: Int,
    val image: String,
    val liked: Int,
    val location: String,
    val offer_value: Int,
    val price: Int,
    val product_name: String,
    val quantity: Int
)