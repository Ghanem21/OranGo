package com.example.orango.ui.home

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repo :RepoImpl
    init {
        val database = OranGoDataBase.getInstance(application.applicationContext)
        repo = RepoImpl(database)
    }

    private val savedCustomerDataLiveData = MutableLiveData<CustomerData>()
    val savedCustomerData : LiveData<CustomerData> = savedCustomerDataLiveData

    val bestSellingProductsTopFive = repo.bestSellingProductsTopFive
    val categoriesTopFive = repo.categoriesTopFive
    val offersTopFive = repo.offerProductsTopFive

    private val sharedPreferences by lazy {
        application.applicationContext.getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    init {
        viewModelScope.launch {
            launch {
                try {
                    val customerDataJson = sharedPreferences.getString("customer_data", null)
                    savedCustomerDataLiveData.value =
                        Gson().fromJson(customerDataJson, CustomerData::class.java)
                    repo.refreshProducts(savedCustomerDataLiveData.value!!.user.id )
                } catch (ex: Exception) {
                    Toast.makeText(getApplication(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                }
            }

            launch {
                try {
                    repo.refreshCategories()
                }catch (ex :Exception){
                    Toast.makeText(getApplication(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
                    ex.printStackTrace()
                }
            }
        }
    }
}