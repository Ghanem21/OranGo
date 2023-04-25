package com.example.data.roomDB
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.data.roomDB.entities.CategoryEntity
import com.example.data.roomDB.entities.NoteEntity
import com.example.data.roomDB.entities.ProductEntity
import com.example.data.roomDB.entities.ReceiptEntity
import com.example.domain.useCase.GetProducts

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(products: List<ProductEntity>)

    //getAllProducts
    @Query("SELECT * FROM products")
    fun getProducts(): LiveData<List<ProductEntity>>

    //get product information page
    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductInfo(id: Int): List<ProductEntity>

    //get similar products in product info page
    @Query("SELECT * FROM products WHERE categoryId = :categoryId LIMIT 10")
    fun getSimilarProducts(categoryId: Int): List<ProductEntity>

    // get products list by category id in categories screen
    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    fun getProductsByCategoryId(categoryId: Int): List<ProductEntity>


    //return bestselling list in home page
    @Query("SELECT * FROM products ORDER BY sold_units DESC LIMIT 5")
    fun getSubBestSelling(): List<ProductEntity>

    //return all bestselling list
    @Query("SELECT * FROM products ORDER BY sold_units DESC LIMIT 20")
    fun getAllBestSelling(): List<ProductEntity>



    //return offers list in home page
    @Query("SELECT * FROM products ORDER BY offerValue DESC LIMIT 3")
    fun getSubOffers(): List<ProductEntity>

    //return all offers list
    @Query("SELECT * FROM products WHERE offerValue != 0")
    fun getAllOffers(): List<ProductEntity>


    // update product favourite
    @Update
    fun setProductFavouriteState(product : ProductEntity)

    // get favourite products list
    @Query("SELECT * FROM products WHERE liked = 1")
    fun getFavouriteProducts(): LiveData<List<ProductEntity>>


    // get all categories list
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryEntity>

    // get categories list in home screen
    @Query("SELECT * FROM categories LIMIT 5")
    fun getSubCategories(): List<CategoryEntity>


    // get all list of notes
    @Query("SELECT * FROM note")
    fun getNotes(): List<NoteEntity>

    // insert new note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(){}

    // delete a note
    @Delete
    fun deleteNote(){}

    //delete all notes
    @Delete
    fun deleteAllNotes(){}

    // update a note quantity
    @Update
    fun updateNote(){}


    // return list of receipt history
    @Query("SELECT * FROM receipts")
    fun getReceipts(): List<ReceiptEntity>



}