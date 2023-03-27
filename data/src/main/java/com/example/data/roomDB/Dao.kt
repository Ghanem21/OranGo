package com.example.data.roomDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM products_table")
    fun getProducts(): List<ProductsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(productsList: ProductsEntity)


}