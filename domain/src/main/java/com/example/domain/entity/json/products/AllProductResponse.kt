package com.example.domain.entity.json.products

import com.example.domain.entity.json.ProductJson

data class AllProductResponse(
    val msg: String,
    val products: List<ProductJson>,
    val status: Boolean
)