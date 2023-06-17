package com.example.orango.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.orango.HomeActivity
import com.example.orango.R
import com.example.orango.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            binding.qoute.text = it.getString("QUOTE")
        }

        val topAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.up_to_down_animation)
        binding.topLogo.startAnimation(topAnim)
        val fadedAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.faded_animation)
        binding.logo.startAnimation(fadedAnim)
        binding.qoute.startAnimation(fadedAnim)

        lifecycleScope.launch(Dispatchers.Main) {
            delayedTimeForFragment()
        }
    }

    private suspend fun delayedTimeForFragment() {
        val customerDataJson = sharedPreferences.getString("customer_data", null)
        delay(4000)
        if (customerDataJson == null) {
            findNavController().navigate(R.id.action_splashScreen_to_onBoardingFragment)
        } else {
            requireContext().startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("QUOTE", binding.qoute.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
