package com.example.domain.entity.json.notes

import com.example.domain.entity.json.ProductJson

data class Note (
    val product: ProductJson,
    val quantity: Int
   )