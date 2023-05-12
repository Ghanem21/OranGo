package com.example.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.remote.Api
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.data.roomDB.entities.asDatabaseModel
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.example.domain.entity.json.auth.logIn.User
import com.example.domain.entity.json.auth.signUp.Error
import com.example.domain.entity.json.auth.updateProfile.toUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RepoImpl (private val database: OranGoDataBase) {
    private val favoritesLiveData = MutableLiveData<List<ProductEntity>>(database.orangoDao.getFavouriteProducts().value ?: listOf())
    val favorites :LiveData<List<ProductEntity>> = favoritesLiveData
    var customerData : CustomerData? = null
    var user : User? = null
    var currentError : String? = null
    var signUPError : Error? = null

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

    suspend fun updateProfile(
        id: Int,
        username: String,
        email: String,
        phoneNumber: String,
        password: String,
        imageUri: String
    ) {
        withContext(Dispatchers.IO){
            val idRequestBody = id.toString().toRequestBody(MultipartBody.FORM)
            val usernameRequestBody = username.toRequestBody(MultipartBody.FORM)
            val emailRequestBody = email.toRequestBody(MultipartBody.FORM)
            val phoneNumberRequestBody = phoneNumber.toRequestBody(MultipartBody.FORM)
            val passwordRequestBody = password.toRequestBody(MultipartBody.FORM)
            val imagePart = if(imageUri.isNotBlank()) {
                val file = File(imageUri)
                val imageRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                 imageRequestBody.let {
                    MultipartBody.Part.createFormData("image", file.name, it)
                }
            }else
                null

            val updateProfileResponse = Api.retrofitService.updateProfile(idRequestBody,usernameRequestBody, emailRequestBody, phoneNumberRequestBody, passwordRequestBody,imagePart)

            Log.d("TAGGG", "updateProfile: $updateProfileResponse")
            updateProfileResponse.customerData?.let { customerData ->
                this@RepoImpl.user = customerData.toUser()
            }
            return@withContext updateProfileResponse.status
        }
    }
}

fun String.toRequestBody(contentType: MediaType): RequestBody {
    return RequestBody.create(contentType, this)
}


fun File.asRequestBody(mediaType: MediaType?): RequestBody {
    return RequestBody.create(mediaType, this)
}

fun String.toMediaTypeOrNull(): MediaType? {
    return try {
        MediaType.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}