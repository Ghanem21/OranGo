package com.example.orango.ui.receiptHistory

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ReceiptEntity
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ReceiptHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    private val sharedPreferences by lazy {
        application.applicationContext.getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    private val receiptsLiveData = MutableLiveData<List<ReceiptEntity>>()
    val receipts : LiveData<List<ReceiptEntity>> = receiptsLiveData
    init{
        viewModelScope.launch {
            try {
                val customerDataJson = sharedPreferences.getString("customer_data", null)
                val savedCustomerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
                receiptsLiveData.value = repo.getReceiptHistory(savedCustomerData.user.id)
            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }
    }
}