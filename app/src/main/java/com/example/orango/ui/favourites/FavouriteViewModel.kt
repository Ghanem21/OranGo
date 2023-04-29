package com.example.orango.ui.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    val favourites = repo.favorites

    fun refreshFavourite(customerId : Int){
        viewModelScope.launch {
            try {
                repo.refreshFavourites(customerId)
            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }
    }
}