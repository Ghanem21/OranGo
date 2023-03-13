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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        if(savedInstanceState != null) {

            binding.qoute.text = savedInstanceState.getString("QOUTE")
            binding.qoute.setText(binding.qoute.text)

        /* binding.topLogo = savedInstanceState.getString("TOP LOGO")
            binding.topLogo.setText(binding.topLogo)

            binding.logo = savedInstanceState.getString("LOGO")
            binding.logo.setText(binding.logo)
         */
        }

        val topAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.up_to_down_animation)
        binding.topLogo.startAnimation(topAnim)
        val fadedAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.faded_animation)
        binding.logo.startAnimation(fadedAnim)
        binding.qoute.startAnimation(fadedAnim)

        GlobalScope.launch(Dispatchers.Main) {
            delayedTimeForFragment()
        }
    }
    private suspend fun delayedTimeForFragment(){
        delay(4000)
        findNavController().navigate(R.id.action_splashScreen_to_onBoardingFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("QOUTE", binding.qoute.toString())
        outState.putString("TOP LOGO",binding.topLogo.toString())
        outState.putString("LOGO",binding.logo.toString())
    }
}
