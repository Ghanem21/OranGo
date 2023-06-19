package com.example.orango.ui.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orango.R
import com.example.orango.databinding.FragmentOfferBinding
import com.example.orango.ui.home.ProductRecyclerViewAdapter

class OfferFragment : Fragment() {
    private val viewModel: OfferViewModel by viewModels()
    private var _binding: FragmentOfferBinding? = null
    private val binding get() = _binding!!

    private val productRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(viewModel.offerProducts.value?.toMutableList() ?: mutableListOf(), 2)
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateOfferAdapter()

        binding.backImage.setOnClickListener {
            findNavController().navigate(R.id.action_offerFragment_to_homeFragment)
        }

        viewModel.offerProducts.observe(viewLifecycleOwner) { products ->
            productRecyclerViewAdapter.updateList(products)
        }
    }

    private fun populateOfferAdapter() {
        binding.productRecycleView.layoutManager = linearLayoutManager

        val adapter = productRecyclerViewAdapter
        binding.productRecycleView.adapter = adapter
    }
}