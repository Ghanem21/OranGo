package com.example.orango.ui.signup

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: RepoImpl

    private val emailErrorLiveData = MutableLiveData<String?>()
    val emailError: LiveData<String?> = emailErrorLiveData

    private val phoneNumberErrorLiveData = MutableLiveData<String?>()
    val phoneNumberError: LiveData<String?> = phoneNumberErrorLiveData

    private val signUpSucceedLiveData = MutableLiveData<Boolean>()
    val signUpSucceed: LiveData<Boolean> = signUpSucceedLiveData
    init {
        val database = OranGoDataBase.getInstance(application.applicationContext)
        repo = RepoImpl(database)
    }

    fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        }

        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matchEntire(email) != null
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length >= 11
    }

    fun validateUsername(username: String): Boolean {
        return username.length >= 3
    }

    fun signUp(username: String, email: String, phoneNumber: String, password: String) {
        viewModelScope.launch {
            try {
                val status = repo.signUp(username, email, phoneNumber, password)
                if (!status) {
                    handleError("Sign up failed")
                    return@launch
                }
                handleSuccess()
            } catch (ex: Exception) {
                handleError("Bad internet Connection")
                ex.printStackTrace()
            }
        }
    }

    private fun handleSuccess() {
        emailErrorLiveData.value = null
        phoneNumberErrorLiveData.value = null
        signUpSucceedLiveData.value = true
    }

    private fun handleError(errorMessage: String) {
        when (errorMessage) {
            "Email error" -> emailErrorLiveData.value = "Invalid email"
            "PhoneNumber error" -> phoneNumberErrorLiveData.value = "Invalid phone number"
            else -> {
                Toast.makeText(getApplication(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    fun signUpDone() {
        signUpSucceedLiveData.value = false
    }
}