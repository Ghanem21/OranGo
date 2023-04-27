package com.example.orango.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindButtons()

        viewModel.phoneNumberError.observe(viewLifecycleOwner){error->
            binding.emailTextInputLayout.error = error
        }

        viewModel.emailError.observe(viewLifecycleOwner){error->
            binding.emailTextInputLayout.error = error
        }
    }

    private fun bindButtons() {
        binding.logInButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        binding.signUpButton.setOnClickListener {
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

            viewModel.signUp(username, email, phoneNumber, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}