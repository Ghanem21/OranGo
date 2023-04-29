package com.example.orango.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.databinding.FragmentFavouritesBinding
import com.example.orango.ui.home.ProductRecyclerViewAdapter

class FavouritesFragment : Fragment() {
    private val viewModel: FavouriteViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val productRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(viewModel.favourites.value?.toMutableList() ?: mutableListOf(), 2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateFavouriteAdapter()

        binding.backImage.setOnClickListener {
            findNavController().navigate(R.id.action_favouritesFragment_to_servicesFragment)
        }

        viewModel.favourites.observe(viewLifecycleOwner) {
            productRecyclerViewAdapter.updateList(it ?: listOf())
        }
        viewModel.refreshFavourite()
    }

    private fun populateFavouriteAdapter() {
        val adapter = productRecyclerViewAdapter
        binding.productRecycleView.adapter = adapter
    }
}