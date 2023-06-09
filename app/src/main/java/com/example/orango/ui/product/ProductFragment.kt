package com.example.orango.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.orango.R
import com.example.orango.databinding.FragmentProductBinding
import com.example.orango.ui.home.ProductRecyclerViewAdapter
import com.example.orango.util.PRODUCT_ID

class ProductFragment : Fragment() {
    private lateinit var binding:FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()

    private val similarProductsAdapter by lazy {
        ProductRecyclerViewAdapter(viewModel.smilierProduct.value?.toMutableList() ?: mutableListOf(),1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loveButton.isEnabled = false
        val arguments = requireArguments()

        val productId = arguments.getInt(PRODUCT_ID)
        viewModel.getProduct(productId)

        viewModel.product.observe(viewLifecycleOwner){ product ->
            binding.productName.text = product.productName
            binding.productPrice.text = "${product.price}$"
            binding.productLocation.text = product.location
            Glide.with(requireContext())
                .load(product.image)
                .centerCrop()
                .apply(RequestOptions().override(1600, 1600).timeout(6000))
                .placeholder(R.drawable.tomato)
                .into(binding.productImg)
            binding.loveButton.isEnabled = true
        }

        populateSimilarProductAdapter()

        viewModel.smilierProduct.observe(viewLifecycleOwner){ products->
            similarProductsAdapter.updateList(products)
        }

        binding.loveButton.setOnClickListener {
            viewModel.updateFavourite()
        }
    }

    private fun populateSimilarProductAdapter() {
        val adapter = similarProductsAdapter
        binding.recycleView.adapter = adapter
    }
}