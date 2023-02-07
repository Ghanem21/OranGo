package com.example.orango.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment(){
    private var _binding: FragmentSplashScreenBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.up_to_down_animation)
        binding.topLogo.startAnimation(topAnim)
        val fadedAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.faded_animation)
        binding.logo.startAnimation(fadedAnim)
        binding.qoute.startAnimation(fadedAnim)

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashScreen_to_onBoardingFragment)
        }, 4000) // 4000 is the delayed time in milliseconds.

    }
}
