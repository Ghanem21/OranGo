package com.example.orango.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orango.R
import com.example.orango.data.DataManager
import com.example.orango.databinding.FragmentHomeBinding
import com.example.orango.util.ViewPager

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val productAdapter by lazy {
        ProductRecyclerViewAdapter(ArrayList())
    }

    private val categoryAdapter by lazy {
        CategoryRecyclerViewAdapter(ArrayList())
    }

    private val offerViewPager by lazy {
        ViewPager(
            DataManager.offerFragmentList,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun populateBestSellingAdapter() {
        val adapter = productAdapter
        binding.content.bestSellingRecyclerView.adapter = adapter
    }

    private fun populateCategoryAdapter() {
        val adapter = categoryAdapter
        binding.content.categoryRecycleView.adapter = adapter
    }

    private fun initViewPager(){
        val adapter = offerViewPager
        binding.content.offerViewPager.adapter = adapter
    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}