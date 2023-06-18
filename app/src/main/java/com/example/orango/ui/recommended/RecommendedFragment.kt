package com.example.orango.ui.recommended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.databinding.FragmentRecommendedBinding
import com.example.orango.ui.home.ProductRecyclerViewAdapter

class RecommendedFragment : Fragment() {
    private val viewModel: RecommendedViewModel by viewModels()
    private var _binding: FragmentRecommendedBinding? = null
    private val binding get() = _binding!!
    private val productRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(viewModel.products.value?.toMutableList() ?: mutableListOf(), 2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        binding.backImage.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.searchImage.setOnClickListener {
            findNavController().navigate(R.id.action_recommendedFragment_to_searchFragment)
        }

        viewModel.products.observe(viewLifecycleOwner){ products ->
            productRecyclerViewAdapter.updateList(products)
        }
    }

    private fun setUpRecyclerView() {
        val adapter = productRecyclerViewAdapter
        binding.productRecycleView.adapter = adapter
    }
}