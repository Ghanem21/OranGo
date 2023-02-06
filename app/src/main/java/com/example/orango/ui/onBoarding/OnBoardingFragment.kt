package com.example.orango.ui.onBoarding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.orango.MainActivity
import com.example.orango.R
import com.example.orango.data.onBoarding.OnBoardingDataManager
import com.example.orango.databinding.FragmentOnBoardingBinding
import com.example.orango.util.BODY
import com.example.orango.util.IMAGE_ID
import com.example.orango.util.TITLE

class OnBoardingFragment : Fragment() {


    private val viewModel: OnBoardingViewModel by viewModels()
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
        binding.indicator.setViewPager(binding.onBoardingFragments)

        binding.nextButton.setOnClickListener{
            viewModel.inc()
        }

        binding.backButton.setOnClickListener {
            viewModel.dec()
        }

        viewModel.currentPosition.observe(viewLifecycleOwner){
            binding.onBoardingFragments.currentItem = it
            if(it == 0)
                binding.backButton.visibility = View.INVISIBLE
            else if (it == 3)

            else
                binding.backButton.visibility = View.VISIBLE

        }
    }
    private fun initViewPager(){
        val fragmentList = ArrayList<Fragment>()
        val data = OnBoardingDataManager.data

        for (item in data){
            val bundle = Bundle()

            bundle.putInt(IMAGE_ID,item.imageId)
            bundle.putInt(TITLE,item.title)
            bundle.putInt(BODY,item.body)

            val fragment = ViewPagerFragment()
            fragment.arguments = bundle

            fragmentList.add(fragment)

        }
        val adapter = OnBoardingViewPager(fragmentList,childFragmentManager,viewLifecycleOwner.lifecycle)
        binding.onBoardingFragments.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}