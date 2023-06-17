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

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()

    private val similarProductsAdapter by lazy {
        ProductRecyclerViewAdapter(
            viewModel.smilierProduct.value?.toMutableList() ?: mutableListOf(), 1
        )
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

        val productId = arguments.getInt("productId")
        viewModel.getProduct(productId)

        viewModel.product.observe(viewLifecycleOwner) { product ->
            binding.productName.text = product.productName
            binding.productPrice.text = "${product.price - (product.price * (product.offerValue / 100f))}$"
            binding.productLocation.text = product.location
            Glide.with(requireContext())
                .load(product.image)
                .apply(RequestOptions().override(1600, 1600).timeout(6000))
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_img)
                .into(binding.productImg)
            product?.let {
                binding.loveButton.isEnabled = true
            }
            if(product.liked == 0)
                binding.loveButton.setImageResource(R.drawable.heart_icon)
            else
                binding.loveButton.setImageResource(R.drawable.baseline_favorite_24)
        }

        populateSimilarProductAdapter()

        viewModel.smilierProduct.observe(viewLifecycleOwner) { products ->
            similarProductsAdapter.updateList(products ?: listOf())
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