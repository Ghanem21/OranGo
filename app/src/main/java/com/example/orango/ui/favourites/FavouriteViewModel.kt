package com.example.orango.ui.favourites

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private var repo : RepoImpl

    private val favouritesLiveData = MutableLiveData<List<ProductEntity>>()
    val favourites = favouritesLiveData

    private var savedCustomerData :CustomerData? = null

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
                favouritesLiveData.value = repo.favouriteProducts.value
            }catch (ex:Exception){
                Toast.makeText(getApplication(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }

    fun refreshFavourite(){
        viewModelScope.launch {
            try {
                favouritesLiveData.value = savedCustomerData?.user?.id?.let {
                    repo.getFavouriteProduct(it)
                }
            }catch (ex:Exception){
                Toast.makeText(getApplication(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }
}