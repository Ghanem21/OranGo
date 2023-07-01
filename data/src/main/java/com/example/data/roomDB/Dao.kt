package com.example.data.roomDB
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.data.roomDB.entities.CategoryEntity
import com.example.data.roomDB.entities.NoteEntity
import com.example.data.roomDB.entities.ProductEntity
import com.example.data.roomDB.entities.ReceiptEntity

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

    @Query("SELECT * FROM products WHERE productName = :productName")
    fun getProductByName(productName: String): List<ProductEntity>

    //get similar products in product info page
    @Query("SELECT * FROM products WHERE categoryId = :categoryId LIMIT 10")
    fun getSimilarProducts(categoryId: Int): LiveData<List<ProductEntity>>

    // get products list by category id in categories screen
    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    fun getProductsByCategoryId(categoryId: Int): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryById(categoryId: Int): List<CategoryEntity>



    //return bestselling list in home page
    @Query("SELECT * FROM products ORDER BY sold_units DESC LIMIT 5")
    fun getSubBestSelling(): LiveData<List<ProductEntity>>

    //return all bestselling list
    @Query("SELECT * FROM products ORDER BY sold_units DESC")
    fun getAllBestSelling(): LiveData<List<ProductEntity>>



    //return offers list in home page
    @Query("SELECT * FROM products ORDER BY offerValue != 0 DESC LIMIT 3")
    fun getSubOffers(): LiveData<List<ProductEntity>>

    //return all offers list
    @Query("SELECT * FROM products ORDER BY offerValue")
    fun getAllOffers(): LiveData<List<ProductEntity>>


    // update product favourite
    @Update
    fun setProductFavouriteState(product : ProductEntity)

    // get favourite products list
    @Query("SELECT * FROM products WHERE liked = 1")
    fun getFavouriteProducts(): LiveData<List<ProductEntity>>


    // get all categories list
    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<CategoryEntity>>

    // get categories list in home screen
    @Query("SELECT * FROM categories LIMIT 5")
    fun getSubCategories(): LiveData<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories : List<CategoryEntity>)


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

    @Query("SELECT * FROM products ORDER BY RANDOM()")
    fun getRecommendedProduct(): LiveData<List<ProductEntity>>

}