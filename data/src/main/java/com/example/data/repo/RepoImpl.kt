package com.example.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.remote.Api
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.data.roomDB.entities.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoImpl (private val database: OranGoDataBase) {
    private val favoritesLiveData = MutableLiveData<List<ProductEntity>>()
    val favorites :LiveData<List<ProductEntity>> = favoritesLiveData
    suspend fun refreshProducts() {

        withContext(Dispatchers.IO) {
            val productsList = Api.retrofitService.getAllProducts(customerId = 1)
            database.orangoDao.addProduct(productsList.products.asDatabaseModel())
        }

    }
    suspend fun refreshFavourites(customerId : Int) {
        withContext(Dispatchers.IO) {
            val favouriteProductsResponse = Api.retrofitService.getFavouriteProducts(customerId = customerId)
            Log.d("TAGGG", "refreshFavourites: ${favouriteProductsResponse.products}")
            favoritesLiveData.value = favouriteProductsResponse.products.asDatabaseModel()
        }
    }

    val products: LiveData<List<ProductEntity>> = database.orangoDao.getProducts()


    suspend fun updatefavorites(product : ProductEntity) {

        withContext(Dispatchers.IO) {
            database.orangoDao.setProductFavouriteState(product)
            if(product.liked == 1) Api.retrofitService.insertToFavourite(1,product.id)
            else Api.retrofitService.deleteFromFavourite(1,product.id)
        }

    }

    suspend fun clearUserDB(){
        withContext(Dispatchers.IO){
            database.clearAllTables()
        }
    }
}