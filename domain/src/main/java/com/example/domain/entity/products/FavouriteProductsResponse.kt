package com.example.domain.entity.products

import com.example.domain.entity.Product

data class FavouriteProductsResponse(
    val msg: String,
    val products: List<Product>,
    val status: Boolean
)