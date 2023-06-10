package com.example.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.remote.Api
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.data.roomDB.entities.asDatabaseModel
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.example.domain.entity.json.auth.signUp.Error
import com.example.domain.entity.json.feedback.AddFeedbackResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoImpl (private val database: OranGoDataBase) {
    private val favoritesLiveData = MutableLiveData<List<ProductEntity>>(database.orangoDao.getFavouriteProducts().value ?: listOf())
    val favorites :LiveData<List<ProductEntity>> = favoritesLiveData
    var customerData : CustomerData? = null
    var currentError : String? = null
    var signUPError : Error? = null

    val sendFeedback: suspend (customerId : Int,message : String) -> AddFeedbackResponse = {customerId,message ->
        withContext(Dispatchers.IO){
            Api.retrofitService.addFeedback(customerId,message)
        }
    }

    suspend fun refreshProducts() {
        withContext(Dispatchers.IO) {
            val productsList = Api.retrofitService.getAllProducts(customerId = 1)
            database.orangoDao.addProduct(productsList.products.asDatabaseModel())
        }

    }
    suspend fun refreshFavourites(customerId : Int) {
        withContext(Dispatchers.IO) {
            val favouriteProductsResponse = Api.retrofitService.getFavouriteProducts(customerId = customerId)
            withContext(Dispatchers.Main) {
                favoritesLiveData.value = favouriteProductsResponse.products.asDatabaseModel()
            }
        }
    }

    val products: LiveData<List<ProductEntity>> = database.orangoDao.getProducts()


    suspend fun updatefavorites(product: ProductEntity) {
        withContext(Dispatchers.IO) {
            database.orangoDao.setProductFavouriteState(product)
            if (product.liked == 1) Api.retrofitService.insertToFavourite(1, product.id)
            else Api.retrofitService.deleteFromFavourite(1, product.id)
        }

    }

    suspend fun clearUserDB() {
        withContext(Dispatchers.IO) {
            database.clearAllTables()
        }
    }

    val favorite: LiveData<List<ProductEntity>> = database.orangoDao.getFavouriteProducts()

    suspend fun logIn(email : String , password : String) : Boolean{
        return withContext(Dispatchers.IO){
            val logInResponse = Api.retrofitService.logIn(email, password)
            logInResponse.customerData?.let { customerData ->
                this@RepoImpl.customerData = customerData
            }
            logInResponse.error?.let {error ->
                currentError = error
            }
            return@withContext logInResponse.status
        }
    }

    suspend fun signUp(
        username: String,
        email: String,
        phoneNumber: String,
        password: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            val signUpResponse = Api.retrofitService.signUp(username, email, phoneNumber, password)
            signUpResponse.error?.let { error ->
                signUPError = error
            }
            return@withContext signUpResponse.status
        }
    }
}