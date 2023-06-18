package com.example.orango.ui.product

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productLiveData = MutableLiveData<ProductEntity>()
    val product: LiveData<ProductEntity> = productLiveData

    private val smilierProductLiveData = MutableLiveData<List<ProductEntity>>()
    var smilierProduct: LiveData<List<ProductEntity>> = smilierProductLiveData

    private val repo: RepoImpl

    private var savedCustomerData : CustomerData? = null

    private val sharedPreferences by lazy {
        application.applicationContext.getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    init {
        val database = OranGoDataBase.getInstance(application.applicationContext)
        repo = RepoImpl(database)
        viewModelScope.launch {
            try {
                val customerDataJson = sharedPreferences.getString("customer_data", null)
                savedCustomerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
            }catch (ex:Exception){
                Toast.makeText(getApplication(),ex.message,Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }

    fun getProduct(productId: Int) {
        viewModelScope.launch {
            try {
                val product = repo.getProducts(productId)
                product?.let {
                    val similarProducts = repo.getSimilarProducts(it.categoryId)
                    smilierProductLiveData.value = similarProducts
                    productLiveData.value = it
                }
            } catch (ex: Exception) {
                Toast.makeText(getApplication(), ex.message, Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }


    fun updateFavourite() {
        viewModelScope.launch {
            product.value?.let {
                val liked = if (it.liked == 0) 1 else 0
                val product = ProductEntity(
                    it.id,
                    it.categoryId,
                    it.categoryName,
                    it.image,
                    liked,
                    it.location,
                    it.offerValue,
                    it.price,
                    it.productName,
                    it.quantity,
                    it.sold_units
                )
                productLiveData.value = product
                try {
                    savedCustomerData?.user?.id?.let { it1 -> repo.updateFavorites(it1,product) }
                } catch (ex: Exception) {
                    Toast.makeText(getApplication(), ex.message, Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                }
            }
        }
    }
}