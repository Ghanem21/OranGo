package com.example.orango.ui.bestSelling

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.launch

class BestSellingViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

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
                val savedCustomerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
                Log.d("logIn", "onViewCreated: $savedCustomerData")
                repo.refreshProducts(savedCustomerData.user.id)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    val bestSellingProduct = repo.bestSellingProduct
}