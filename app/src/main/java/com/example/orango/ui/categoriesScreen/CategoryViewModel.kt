package com.example.orango.ui.categoriesScreen

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.data.roomDB.entities.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    val categories = repo.categories
    private val productsLiveData = MutableLiveData<List<ProductEntity>>()
    val products: LiveData<List<ProductEntity>> = productsLiveData

    init {
        viewModelScope.launch {
            try {
                repo.refreshCategories()
            } catch (ex: Exception) {
                Toast.makeText(getApplication(), "Bad internet Connection", Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }

    fun getProductByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            try {
                productsLiveData.value = repo.getProductByCategoryId(categoryId)
            } catch (ex: Exception) {
                Toast.makeText(getApplication(),"Bad internet Connection", Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
            }
        }
    }

    suspend fun getCategoryById(categoryId: Int) = withContext(Dispatchers.IO) {
        try {
            repo.getCategoryById(categoryId)
        } catch (ex: Exception) {
            Toast.makeText(getApplication(), "Bad internet Connection", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
            null
        }
    }
}
