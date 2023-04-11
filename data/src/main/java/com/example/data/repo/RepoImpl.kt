package com.example.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.remote.Api
import com.example.data.roomDB.OranGoDataBase
import com.example.domain.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoImpl (private val database: OranGoDataBase) {

    suspend fun refreshProducts() {

        withContext(Dispatchers.IO) {
            val productslist = Api.retrofitService.getAllProducts(customerId = 1)
          //  database.orangoDao.getProducts(productslist.)

        }

    }

//    val products: LiveData<List<Product>> = Transformations.map(database.orangoDao.getProducts()) {
//        it.asDomainModel()
//    }

}