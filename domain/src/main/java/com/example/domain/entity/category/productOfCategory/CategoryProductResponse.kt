package com.example.domain.entity.category.productOfCategory

import com.example.domain.entity.Product

data class CategoryProductResponse(
    val msg: String,
    val products: List<Product>,
    val status: Boolean
)