package com.example.orango.ui.point

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

class PointViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    private val pointsLiveData = MutableLiveData<Int>()
    val points : LiveData<Int> = pointsLiveData

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
                pointsLiveData.value = repo.getNumberOfPoints(savedCustomerData.user.id)
            }catch (ex:Exception){
                Toast.makeText(getApplication(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }


}