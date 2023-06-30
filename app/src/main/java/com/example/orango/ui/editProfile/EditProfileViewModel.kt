package com.example.orango.ui.editProfile

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditProfileViewModel(private val application: Application) : AndroidViewModel(application) {
    private val database = OranGoDataBase.getInstance(application.applicationContext)
    private val repo = RepoImpl(database)

    private val sharedPreferences by lazy {
        application.applicationContext.getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }
    val editor by lazy { sharedPreferences.edit() }
    var customerData: CustomerData

    init {
        val customerDataJson = sharedPreferences.getString("customer_data", null)
        customerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
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

    suspend fun updateProfile(username: String, email: String, phoneNumber: String, password: String) =
        withContext(Dispatchers.Main) {
            try {
                val imageUri = (sharedPreferences.getString("imageUri","") ?: "").toUri()
                val imagePath = if (imageUri.path?.isNotEmpty() == true) getRealPathFromURI(imageUri) else ""
                repo.updateProfile(customerData.user.id, username, email, phoneNumber, password, imagePath.orEmpty())
                repo.user?.password = password
                customerData.user = repo.user ?: customerData.user
                editor.putString("customer_data", Gson().toJson(customerData))
                editor.apply()
                return@withContext true
            } catch (ex: Exception) {
                Toast.makeText(getApplication(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
                ex.printStackTrace()
                return@withContext false
            }
        }


    private fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = application.contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = cursor?.getString(columnIndex ?: -1)
        cursor?.close()
        return path
    }
}