package com.example.orango.ui.editProfile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.orango.R
import com.example.orango.databinding.FragmentEditProfileBinding

const val PICK_IMAGE_REQUEST = 1
const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 2
class EditProfileFragment : Fragment() {

    private val viewModel: EditProfileViewModel by viewModels()
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = viewModel.customerData.user

        binding.emailEditText.setText(user.email)
        binding.usernameEditText.setText(user.user_name)
        binding.passwordEditText.setText(user.password)
        binding.phoneEditText.setText(user.phone_number)
        Glide.with(binding.root.context)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(binding.profileImage)

        binding.pickPhotoFab.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE_REQUEST_CODE)
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            }
        }

        binding.saveChangeButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            if (!viewModel.validateUsername(username)) {
                binding.usernameTextInputLayout.error =
                    "Username must be at least 3 characters long"
                return@setOnClickListener
            }else{
                binding.usernameTextInputLayout.error = null
            }

            val email = binding.emailEditText.text.toString().trim()
            if (!viewModel.validateEmail(email)) {
                binding.emailTextInputLayout.error = "Please enter a valid email address"
                return@setOnClickListener
            }else{
                binding.emailTextInputLayout.error = null
            }

            val phoneNumber = binding.phoneEditText.text.toString().trim()
            if (!viewModel.validatePhoneNumber(phoneNumber)) {
                binding.phoneTextInputLayout.error = "Phone number must be at least 11 characters long"
                return@setOnClickListener
            }else{
                binding.phoneTextInputLayout.error = null
            }

            val password = binding.passwordEditText.text.toString().trim()
            if (!viewModel.validatePassword(password)) {
                binding.passwordTextInputLayout.error = "Password must be at least 8 characters long"
                return@setOnClickListener
            }else{
                binding.passwordTextInputLayout.error = null
            }

            viewModel.updateProfile(username, email, phoneNumber,password)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data

            binding.profileImage.setImageURI(imageUri)

            Log.d("TAG", "onActivityResult: ${imageUri?.path}")
            viewModel.editor.putString("imageUri",imageUri.toString())
            viewModel.editor.apply()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        } else {
            Toast.makeText(requireContext(),"You can't change profile image without give us permission",Toast.LENGTH_LONG).show()
        }
    }
}