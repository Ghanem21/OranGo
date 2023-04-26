package com.example.orango.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.domain.entity.json.auth.logIn.CustomerData
import kotlinx.coroutines.launch

class LogInViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    private val messageLiveData = MutableLiveData<String>()
    val message: LiveData<String> = messageLiveData

    private val showToastFlagLiveData = MutableLiveData<Boolean>()
    val showToastFlag: LiveData<Boolean> = showToastFlagLiveData

    private val customerDataLiveData = MutableLiveData<CustomerData>()
    val customerData: LiveData<CustomerData> = customerDataLiveData
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
        if (password.length < 8) {
            return false
        }
        return true
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
                    this@LogInViewModel.customerDataLiveData.value = customerData
                }
            } catch (ex: Exception) {
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
}