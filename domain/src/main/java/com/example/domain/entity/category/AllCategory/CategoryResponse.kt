package com.example.domain.entity.category.AllCategory

data class CategoryResponse(
    val categories: List<Category>,
    val msg: String,
    val status: Boolean
)