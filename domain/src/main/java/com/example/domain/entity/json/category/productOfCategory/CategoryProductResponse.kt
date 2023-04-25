package com.example.domain.entity.json.category.productOfCategory

import com.example.domain.entity.json.ProductJson

data class CategoryProductResponse(
    val msg: String,
    val products: List<ProductJson>,
    val status: Boolean
)