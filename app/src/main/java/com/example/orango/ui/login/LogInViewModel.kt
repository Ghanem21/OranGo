package com.example.orango.ui.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LogInViewModel(application: Application) : AndroidViewModel(application) {

    private val repo : RepoImpl

    private val messageLiveData = MutableLiveData<String>()
    val message: LiveData<String> = messageLiveData

    private val showToastFlagLiveData = MutableLiveData<Boolean>()
    val showToastFlag: LiveData<Boolean> = showToastFlagLiveData

    private val logInSucceedLiveData = MutableLiveData<Boolean>()
    val logInSucceed: LiveData<Boolean> = logInSucceedLiveData

    private val sharedPreferences: SharedPreferences by lazy {
        application.getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    private val editor: SharedPreferences.Editor by lazy { sharedPreferences.edit() }

    init {
        val database = OranGoDataBase.getInstance(application.applicationContext)
        repo = RepoImpl(database)
    }

    fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        }

        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        if (!emailRegex.matches(email)) {
            return false
        }

        return true
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                val status = repo.logIn(email, password)
                if (!status) {
                    messageLiveData.value = repo.currentError
                    showToast()
                    return@launch
                }

                repo.customerData?.let { customerData ->
                    editor.putString("customer_data", Gson().toJson(customerData))
                    editor.apply()
                    logInSucceedLiveData.value = true
                }
            } catch (ex: Exception) {
                Toast.makeText(getApplication(),ex.message,Toast.LENGTH_LONG).show()
                ex.printStackTrace()
            }
        }
    }

    private fun showToast() {
        showToastFlagLiveData.value = true
    }

    fun showToastDone() {
        showToastFlagLiveData.value = false
    }

    fun logInDone() {
        logInSucceedLiveData.value = false
    }
}