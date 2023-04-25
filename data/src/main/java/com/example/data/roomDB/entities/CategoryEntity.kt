package com.example.data.roomDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.json.category.AllCategory.Category


@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val image: String,
    val name: String
)

fun List<CategoryEntity>.asJsonModel(): List<Category> {
    return map {
        Category(
            id = it.id,
            image = it.image,
            name = it.name
        )
    }
}

fun List<Category>.asDatabaseModel(): List<CategoryEntity> {
    return map {
        CategoryEntity(
            id = it.id,
            image= it.image,
            name = it.name
        )
    }
}