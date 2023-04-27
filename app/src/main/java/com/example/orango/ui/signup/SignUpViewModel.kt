package com.example.orango.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import kotlinx.coroutines.launch

class SignUpViewModel (application: Application) : AndroidViewModel(application) {

    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    private val emailErrorLiveData = MutableLiveData<String?>()
    val emailError : LiveData<String?> = emailErrorLiveData

    private val phoneNumberErrorLiveData = MutableLiveData<String?>()
    val phoneNumberError : LiveData<String?> = phoneNumberErrorLiveData

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

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.length < 11) {
            return false
        }
        return true
    }

    fun validateUsername(username: String): Boolean {
        if (username.length < 3) {
            return false
        }
        return true
    }

    fun signUp(username:String,email: String,phoneNumber:String, password: String) {
        viewModelScope.launch {
            try {
                val status = repo.signUp(username,email,phoneNumber, password)
                if (!status) {
                    handleError()
                    return@launch
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun handleError() {
        repo.signUPError?.email?.getOrNull(0)?.let {
            emailErrorLiveData.value = it
        }

        repo.signUPError?.phoneNumber?.getOrNull(0)?.let {
            phoneNumberErrorLiveData.value = it
        }
    }
}