package com.example.orango.ui.bestSelling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.orango.R
import com.example.orango.databinding.FragmentBestSellingBinding

class BestSellingFragment : Fragment() {
    private val viewModel: BestSellingViewModel by viewModels()
    private var _binding: FragmentBestSellingBinding? = null
    private val binding get() = _binding!!

    private val productRecyclerViewAdapter by lazy {
        BestSellingAdapter(mutableListOf())
    }

    private val gridLayoutManager by lazy {
        GridLayoutManager(requireContext(), 2)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBestSellingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateBestSellingAdapter()
        binding.backImage.setOnClickListener {
            findNavController().navigate(R.id.action_bestSellingFragment_to_homeFragment)
        }
        binding.searchImage.setOnClickListener {
            findNavController().navigate(R.id.action_bestSellingFragment_to_searchFragment)
        }

        viewModel.bestSellingProduct.observe(viewLifecycleOwner){ products->
            productRecyclerViewAdapter.updateList(products)
        }
    }

    private fun populateBestSellingAdapter() {
        binding.productList.layoutManager = gridLayoutManager

        val adapter = productRecyclerViewAdapter
        binding.productList.adapter = adapter
    }
}