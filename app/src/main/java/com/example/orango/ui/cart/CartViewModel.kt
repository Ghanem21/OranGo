package com.example.orango.ui.cart

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)
    private lateinit var savedCustomerData: CustomerData

    val products = mutableListOf<ProductEntity>()

    val quantities = mutableListOf<Int>()

    private val productsLiveData = MutableLiveData<List<ProductEntity>>()
    val productsLive: LiveData<List<ProductEntity>> = productsLiveData

    private val quantitiesLiveData = MutableLiveData<List<Int>>()
    val quantitiesLive: LiveData<List<Int>> = quantitiesLiveData

    private val sharedPreferences by lazy {
        application.applicationContext.getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    init {
        viewModelScope.launch {
            try {
                val customerDataJson = sharedPreferences.getString("customer_data", null)
                savedCustomerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
                repo.refreshProducts(1)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    suspend fun detectProduct(imageFile: File) =
        withContext(Dispatchers.IO) {
            val aiResponse = repo.detectProduct(imageFile)
            Log.d("TAG", "detectProduct: ${aiResponse.items}")
            aiResponse.items.forEach { productName ->
                getProductByName(productName)
            }
            quantities.addAll(aiResponse.quantities)
            withContext(Dispatchers.Main) {
                productsLiveData.value = products
                quantitiesLiveData.value = quantities
            }
        }


    private fun getProductByName(productName: String) {
        viewModelScope.launch {
            val product = repo.getProductByName("product 1")
            Log.d("TAG", "detectProduct: ${product}")
            product?.let {
                products.add(product)
            }
        }
    }
}