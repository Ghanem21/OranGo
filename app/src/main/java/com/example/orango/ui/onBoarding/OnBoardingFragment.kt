package com.example.orango.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.orango.R
import com.example.orango.data.onBoarding.DataManager
import com.example.orango.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {


    private val viewModel by lazy {
        ViewModelProvider(this)[OnBoardingViewModel::class.java]
    }
    private val onBoardingViewPager by lazy {
        OnBoardingViewPager(
            DataManager.fragmentList,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
    }

    private var _binding: FragmentOnBoardingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

        if(viewModel.isNewlyCreated) {
            savedInstanceState?.let {
                viewModel.restoreState(it)
            }
        }

        binding.indicator.setViewPager(binding.onBoardingFragments)

        binding.nextButton.setOnClickListener{
            viewModel.inc()
        }

        binding.backButton.setOnClickListener {
            viewModel.dec()
        }

        binding.skip.setOnClickListener {
            viewModel.setCurrentPosition(4)
        }

        binding.onBoardingFragments.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position != viewModel.currentPosition.value)
                    viewModel.setCurrentPosition(position)
            }
        })

        viewModel.currentPosition.observe(viewLifecycleOwner){
            handleCurrentPosition(it)
        }
    }

    private fun handleCurrentPosition(it: Int) {
        if (it != 4)
            binding.onBoardingFragments.currentItem = it
        when (it) {
            0 -> binding.backButton.visibility = View.INVISIBLE
            4 -> {
                findNavController().navigate(R.id.action_onBoardingFragment_to_logInFragment)
                viewModel.setCurrentPosition(binding.onBoardingFragments.currentItem)
            }
            else -> binding.backButton.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)
    }
    private fun initViewPager(){
        val adapter = onBoardingViewPager
        binding.onBoardingFragments.adapter = adapter
    }

    override fun onPause() {
        binding.onBoardingFragments.adapter = null
        super.onPause()
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}