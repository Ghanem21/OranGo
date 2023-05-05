package com.example.orango.ui.categoriesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orango.databinding.FragmentCategoriesScreenBinding
import com.example.orango.ui.home.CategoryRecyclerViewAdapter
import com.example.orango.ui.home.ProductRecyclerViewAdapter

class CategoriesScreenFragment : Fragment() {
    private val viewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentCategoriesScreenBinding? = null
    private val binding get() = _binding!!
    private val linearLayoutManagerHorizontal by lazy {
        LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private val categoryRecyclerViewAdapter by lazy {
        CategoryRecyclerViewAdapter(viewModel.categories.value?.toMutableList() ?: mutableListOf())
    }

    private val linearLayoutManagerVertical by lazy {
        LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private val productRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(viewModel.product.value?.toMutableList() ?: mutableListOf(),2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateCategoryAdapter()
        populateProductAdapter()

        viewModel.categories.observe(viewLifecycleOwner){ categories ->
            categoryRecyclerViewAdapter.updateList(categories.toMutableList())
        }

        categoryRecyclerViewAdapter.selectedCategory.observe(viewLifecycleOwner){categoryId->
            categoryId?.let {
                viewModel.getProductByCategoryId(categoryId)
            }
        }

        viewModel.product.observe(viewLifecycleOwner){products ->
            productRecyclerViewAdapter.updateList(products)
        }
    }

    private fun populateCategoryAdapter() {
        binding.categoryRecycleView.layoutManager = linearLayoutManagerHorizontal

        val adapter = categoryRecyclerViewAdapter
        binding.categoryRecycleView.adapter = adapter
    }

    private fun populateProductAdapter() {
        binding.productRecycleView.layoutManager = linearLayoutManagerVertical

        val adapter = productRecyclerViewAdapter
        binding.productRecycleView.adapter = adapter
    }
}