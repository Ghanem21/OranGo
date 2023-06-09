package com.example.orango.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productLiveData = MutableLiveData<ProductEntity>()
    val product : LiveData<ProductEntity> = productLiveData

    lateinit var smilierProduct : LiveData<List<ProductEntity>>

    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    fun getProduct(productId : Int){
        viewModelScope.launch {
            productLiveData.value = repo.getProducts(productId)
            productLiveData.value?.categoryId?.let {
                smilierProduct = repo.getSimilarProducts(it)
            }
        }
    }

    fun updateFavourite(){
        viewModelScope.launch {
            product.value?.let {
                val liked = if(it.liked == 0) 1 else 0
                val product = ProductEntity(it.id,it.categoryId,it.categoryName,it.image,liked,it.location,it.offerValue,it.price,it.productName,it.quantity,it.sold_units)
                repo.updatefavorites(product)
            }
        }
    }
}