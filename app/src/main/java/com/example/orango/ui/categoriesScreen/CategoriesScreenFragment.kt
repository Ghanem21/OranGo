package com.example.orango.ui.categoriesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.databinding.FragmentCategoriesScreenBinding
import com.example.orango.ui.home.CategoryRecyclerViewAdapter
import com.example.orango.ui.home.ProductRecyclerViewAdapter
import kotlinx.coroutines.launch

class CategoriesScreenFragment : Fragment() {
    private val viewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentCategoriesScreenBinding? = null
    private val binding get() = _binding!!


    private val categoryRecyclerViewAdapter by lazy {
        CategoryRecyclerViewAdapter(viewModel.categories.value.orEmpty().toMutableList())
    }



    private val productRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(viewModel.products.value.orEmpty().toMutableList(),2)
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
        handleArgs(arguments)

        viewModel.categories.observe(viewLifecycleOwner){ categories ->
            categoryRecyclerViewAdapter.updateList(categories.toMutableList())
        }

        categoryRecyclerViewAdapter.selectedCategory.observe(viewLifecycleOwner){categoryId->
            categoryId?.let {
                updateSelectedCategory(categoryId)
            }
        }

        viewModel.products.observe(viewLifecycleOwner){products ->
            productRecyclerViewAdapter.updateList(products)
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesScreenFragment_to_homeFragment)
        }

        binding.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesScreenFragment_to_searchFragment)
        }
    }

    private fun handleArgs(arguments: Bundle?) {
        val categoryId = arguments?.getInt("categoryId")
        updateSelectedCategory(categoryId)
    }

    private fun updateSelectedCategory(categoryId: Int?) {
        categoryId?.let {
            lifecycleScope.launch {
                val category = viewModel.getCategoryById(categoryId)
                viewModel.getProductByCategoryId(categoryId)
                category?.let {
                    binding.categoryName.text = category.name
                }
            }
        }
    }

    private fun populateCategoryAdapter() {
        val adapter = categoryRecyclerViewAdapter
        binding.categoryRecycleView.adapter = adapter
    }

    private fun populateProductAdapter() {
        val adapter = productRecyclerViewAdapter
        binding.productRecycleView.adapter = adapter
    }
}