package com.example.orango.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.orango.R
import com.example.orango.data.DataManager
import com.example.orango.databinding.FragmentOnBoardingBinding
import com.example.orango.util.ViewPager

class OnBoardingFragment : Fragment() {

    private val viewModel: OnBoardingViewModel by viewModels()
    private lateinit var binding: FragmentOnBoardingBinding
    private val onBoardingViewPager by lazy {
        ViewPager(
            DataManager.onBoardingFragmentList,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

        if (viewModel.isNewlyCreated) {
            savedInstanceState?.let {
                viewModel.restoreState(it)
            }
        }

        binding.indicator.setViewPager(binding.onBoardingFragments)

        binding.nextButton.setOnClickListener {
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
                if (position != viewModel.currentPosition.value)
                    viewModel.setCurrentPosition(position)
            }
        })

        viewModel.currentPosition.observe(viewLifecycleOwner) {
            handleCurrentPosition(it)
        }
    }

    private fun handleCurrentPosition(position: Int) {
        binding.onBoardingFragments.setCurrentItem(position, true)
        when (position) {
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

    private fun initViewPager() {
        binding.onBoardingFragments.adapter = onBoardingViewPager
    }

    override fun onPause() {
        binding.onBoardingFragments.adapter = null
        super.onPause()
    }
}
