package com.example.data.roomDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.json.ProductJson

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

fun List<ProductEntity>.asJsonModel(): List<ProductJson> {
    return map {
        ProductJson(
        id = it.id,
        categoryId= it.categoryId,
        categoryName= it.categoryName,
        image= it.image,
        liked= it.liked,
        location= it.location,
        offerValue= it.offerValue,
        price= it.price,
        productName= it.productName,
        quantity= it.quantity,
        sold_units= it.sold_units)
    }
}

fun List<ProductJson>.asDatabaseModel(): List<ProductEntity> {
    return map {
        ProductEntity(
            id = it.id,
            categoryId= it.categoryId,
            categoryName= it.categoryName,
            image= it.image,
            liked= it.liked,
            location= it.location,
            offerValue= it.offerValue,
            price= it.price,
            productName= it.productName,
            quantity= it.quantity,
            sold_units= it.sold_units)
    }
}