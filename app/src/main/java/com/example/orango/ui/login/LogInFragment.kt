package com.example.orango.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.databinding.FragmentLogInBinding
import com.google.gson.Gson

class LogInFragment : Fragment() {
    private val viewModel: LogInViewModel by viewModels()
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }
    private val editor by lazy { sharedPreferences.edit() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindButtons()

        viewModel.showToastFlag.observe(viewLifecycleOwner) { flag ->
            if (flag) {
                showToast()
                viewModel.showToastDone()
            }
        }

        viewModel.customerData.observe(viewLifecycleOwner) { customerData ->
            editor.putString("customer_data", Gson().toJson(customerData))
            editor.apply()
//            val customerDataJson = sharedPreferences.getString("customer_data", null)
//            val savedCustomerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
//            Log.d("logIn", "onViewCreated: $savedCustomerData")
        }
    }

    private fun showToast() {
        Toast.makeText(requireContext(), viewModel.message.value, Toast.LENGTH_LONG).show()
    }

    private fun bindButtons() {
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        binding.logInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            if (!viewModel.validateEmail(email)) {
                binding.emailTextInputLayout.error = "Please enter a valid email address"
                return@setOnClickListener
            }else{
                binding.emailTextInputLayout.error = null
            }

            val password = binding.passwordEditText.text.toString().trim()
            if (!viewModel.validatePassword(password)) {
                binding.passwordTextInputLayout.error = "Password must be at least 8 characters long"
                return@setOnClickListener
            }else{
                binding.passwordTextInputLayout.error = null
            }

            viewModel.logIn(email, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}