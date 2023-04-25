package com.example.data.repo

import androidx.lifecycle.LiveData
import com.example.data.remote.Api
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.data.roomDB.entities.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoImpl (private val database: OranGoDataBase) {

    suspend fun refreshProducts() {

        withContext(Dispatchers.IO) {
            val productslist = Api.retrofitService.getAllProducts(customerId = 1)
            database.orangoDao.addProduct(productslist.products.asDatabaseModel())
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

    val favorite: LiveData<List<ProductEntity>> = database.orangoDao.getFavouriteProducts()

}