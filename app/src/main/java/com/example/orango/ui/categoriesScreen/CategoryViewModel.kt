package com.example.orango.ui.categoriesScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    val categories = repo.categories
    val product = repo.categoryProducts

    init {
        viewModelScope.launch {
            try {
                repo.refreshCategories()
            }catch (ex :Exception){
                ex.printStackTrace()
            }
        }
    }

    fun getProductByCategoryId(categoryId : Int){
        viewModelScope.launch {
            try {
                repo.getProductByCategoryId(categoryId)
            }catch (ex : Exception){
                ex.printStackTrace()
            }
        }
    }
}