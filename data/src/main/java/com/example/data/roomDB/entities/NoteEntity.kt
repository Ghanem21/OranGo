package com.example.data.roomDB.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName = "note",
    foreignKeys = [
        ForeignKey(entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = CASCADE,
            onUpdate = CASCADE,
            deferred = true
        )
    ])
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val quantity: Int
)


//fun List<NoteEntity>.asJsonModel(): List<Note> {
//    return map {
//        Note(
//            id = it.id,
//            productId = it.productId,
//            quantity = it.quantity
//        )
//    }
//}
//
//fun List<Note>.asDatabaseModel(): List<NoteEntity> {
//    return map {
//        NoteEntity(
//            id = it.id,
//            productId= it.productId,
//            quantity = it.quantity
//        )
//    }
//}
