package com.example.domain.entity.json.category.AllCategory

data class CategoryResponse(
    val categories: List<Category>,
    val msg: String,
    val status: Boolean
)