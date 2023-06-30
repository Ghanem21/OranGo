package com.example.orango.ui.cart

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.task.vision.detector.Detection

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)
    lateinit var savedCustomerData: CustomerData

    private val mapTemp = mutableMapOf<ProductEntity, Int>()

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
                repo.refreshProducts(savedCustomerData.user.id)
            } catch (ex: Exception) {
                Toast.makeText(getApplication(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }

//    suspend fun detectProduct(imageFile: File): MutableMap<ProductEntity, Int> {
//        val map = mutableMapOf<ProductEntity, Int>()
//
//        return viewModelScope.async {
//            return@async try {
//                withContext(Dispatchers.IO) {
//                    val aiResponse = repo.detectProduct(imageFile)
//                    Log.d("TAGGG", "detectProduct: ${aiResponse.items}")
//                    for (index in aiResponse.items.indices) {
//                        getProductByName(aiResponse.items[index], aiResponse.quantities[index])
//                    }
//                    map.putAll(mapTemp)
//                    return@withContext map
//                }
//            } catch (ex: Exception) {
//                Toast.makeText(getApplication(), "Bad internet Connection", Toast.LENGTH_SHORT).show()
//                ex.printStackTrace()
//                return@async map
//            }
//        }.await()
//    }


    private suspend fun getProductByName(productName: String) : ProductEntity? = withContext(Dispatchers.IO) {
        repo.getProductByName(productName)
    }

    suspend fun countProducts(results: MutableList<Detection>?): Map<ProductEntity, Int> {
        val productCounts = mutableMapOf<ProductEntity, Int>()
        results?.forEach { result ->
            val productName = result.categories.firstOrNull()?.label?.replaceFirstChar { it.uppercaseChar() }
            Toast.makeText(getApplication(),productName,Toast.LENGTH_SHORT).show()
            productName?.let { name ->
                val product = getProductByName(name)
                product?.let {
                    productCounts[product] = productCounts.getOrDefault(product, 0) + 1
                }
            }
        }
        return productCounts
    }
}