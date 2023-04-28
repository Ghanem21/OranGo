package com.example.orango.ui.favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.json.auth.logIn.CustomerData
import com.example.orango.R
import com.example.orango.databinding.FragmentFavouritesBinding
import com.example.orango.ui.home.ProductRecyclerViewAdapter
import com.google.gson.Gson

class FavouritesFragment : Fragment() {
    private val viewModel: FavouriteViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val linearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private val productRecyclerViewAdapter by lazy {
        ProductRecyclerViewAdapter(mutableListOf(), 2)
    }

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences(
            "my_shared_preferences",
            Context.MODE_PRIVATE
        )
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

        binding.imageView6.setOnClickListener {
            findNavController().navigate(R.id.action_favouritesFragment_to_servicesFragment)
        }

        viewModel.favourites.observe(viewLifecycleOwner) {
            productRecyclerViewAdapter.updateList(it)
        }

        val customerDataJson = sharedPreferences.getString("customer_data", null)
        val savedCustomerData = Gson().fromJson(customerDataJson, CustomerData::class.java)
        viewModel.refreshFavourite(savedCustomerData.user.id)
    }

    private fun populateFavouriteAdapter() {
        binding.productRecycleView.layoutManager = linearLayoutManager

        val adapter = productRecyclerViewAdapter
        binding.productRecycleView.adapter = adapter
    }
}