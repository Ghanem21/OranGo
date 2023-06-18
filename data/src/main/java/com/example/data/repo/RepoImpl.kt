package com.example.data.repo

import androidx.lifecycle.LiveData
import com.example.data.remote.Api
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.data.roomDB.entities.ReceiptEntity
import com.example.data.roomDB.entities.asDatabaseModel
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.example.domain.entity.json.auth.signUp.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoImpl(private val database: OranGoDataBase) {
    var customerData: CustomerData? = null
    var currentError: String? = null
    private var signUpError: Error? = null

    val bestSellingProduct = database.orangoDao.getAllBestSelling()
    val bestSellingProductsTopFive = database.orangoDao.getSubBestSelling()
    val offerProductsTopFive = database.orangoDao.getSubOffers()
    val categoriesTopFive = database.orangoDao.getSubCategories()
    val favouriteProducts = database.orangoDao.getFavouriteProducts()
    val recommendedProducts = database.orangoDao.getRecommendedProduct()

    val getFavouriteProduct: suspend (customerId: Int) ->  List<ProductEntity> = { customerId ->
        withContext(Dispatchers.IO) {
            Api.retrofitService.getFavouriteProducts(customerId = customerId).products.asDatabaseModel()
        }
    }

    val getProducts: suspend (id: Int) -> ProductEntity? = { id ->
        withContext(Dispatchers.IO) {
            database.orangoDao.getProductInfo(id).getOrNull(0)
        }
    }

    suspend fun refreshProducts(customerId: Int) {
        withContext(Dispatchers.IO) {
            val productsList = Api.retrofitService.getAllProducts(customerId = customerId)
            database.orangoDao.addProduct(productsList.products.asDatabaseModel())
        }
    }

    val getSimilarProducts: suspend (categoryId: Int) -> List<ProductEntity> =
        { categoryId ->
            withContext(Dispatchers.IO) {
                database.orangoDao.getSimilarProducts(categoryId)
            }
        }

    val getReceiptHistory: suspend (customerId: Int) -> List<ReceiptEntity> = { customerId ->
        withContext(Dispatchers.IO) {
            Api.retrofitService.getCustomerReceipt(customerId).receipts.asDatabaseModel()
        }
    }

    suspend fun refreshCategories() {
        withContext(Dispatchers.IO) {
            val categoriesList = Api.retrofitService.getCategory()
            database.orangoDao.insertCategories(categoriesList.categories.asDatabaseModel())
        }
    }


    val products: LiveData<List<ProductEntity>> = database.orangoDao.getProducts()


    suspend fun updateFavorites(customerId:Int,product: ProductEntity) {
        withContext(Dispatchers.IO) {
            database.orangoDao.setProductFavouriteState(product)
            if (product.liked == 1) Api.retrofitService.insertToFavourite(customerId, product.id)
            else Api.retrofitService.deleteFromFavourite(customerId , product.id)
        }

    }

    suspend fun clearUserDB() {
        withContext(Dispatchers.IO) {
            database.clearAllTables()
        }
    }

    suspend fun logIn(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val logInResponse = Api.retrofitService.logIn(email, password)
            logInResponse.customerData?.let { customerData ->
                this@RepoImpl.customerData = customerData
            }
            logInResponse.error?.let { error ->
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
                signUpError = error
            }
            return@withContext signUpResponse.status
        }
    }
}