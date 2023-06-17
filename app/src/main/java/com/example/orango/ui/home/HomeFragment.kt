package com.example.orango.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.data.roomDB.entities.ProductEntity
import com.example.orango.R
import com.example.orango.databinding.FragmentHomeBinding
import com.example.orango.util.PRODUCT_ID
import com.example.orango.util.ViewPager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val productLayoutManager by lazy {
        LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private val categoryLayoutManager by lazy {
        LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private val productBestSellingAdapter by lazy {
        ProductRecyclerViewAdapter(viewModel.bestSellingProductsTopFive.value?.toMutableList() ?: mutableListOf(),1)
    }

    private val categoryAdapter by lazy {
        CategoryRecyclerViewAdapter(viewModel.categoriesTopFive.value?.toMutableList() ?: mutableListOf())
    }

    private val offerViewPagerAdapter by lazy {
        ViewPager(
            mutableListOf(),
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
        populateBestSellingAdapter()
        initViewPager()
        populateCategoryAdapter()
        
        viewModel.bestSellingProductsTopFive.observe(viewLifecycleOwner){ products->
            productBestSellingAdapter.updateList(products)
        }

        viewModel.categoriesTopFive.observe(viewLifecycleOwner){ categories->
            categoryAdapter.updateList(categories)
        }

        viewModel.offersTopFive.observe(viewLifecycleOwner){offers->
            offerViewPagerAdapter.updateList(initOfferFragment(offers))
        }

        viewModel.savedCustomerData.observe(viewLifecycleOwner){ userData ->
            binding.headerLayout.usernameText.text = userData.user.user_name
            Glide.with(requireContext())
                .load(userData.user.image)
                .centerCrop()
                .apply(RequestOptions().override(1600, 1600).timeout(6000))
                .placeholder(R.drawable.profile)
                .into(binding.headerLayout.profileIcon)
        }

        startAutoScroll()
    }

    private fun initOfferFragment(offerProducts : List<ProductEntity>): MutableList<Fragment> {
        val offerFragmentList = mutableListOf<Fragment>()
        for (product in offerProducts){
            val bundle = Bundle()

            bundle.putInt(PRODUCT_ID,product.id)

            val fragment = OfferViewPager()
            fragment.arguments = bundle

            offerFragmentList.add(fragment)
        }
        return offerFragmentList
    }

    private fun populateBestSellingAdapter() {
        binding.content.bestSellingRecyclerView.layoutManager = productLayoutManager
        val adapter = productBestSellingAdapter
        binding.content.bestSellingRecyclerView.adapter = adapter
    }

    private fun populateCategoryAdapter() {
        binding.content.categoryRecycleView.layoutManager = categoryLayoutManager
        val adapter = categoryAdapter
        binding.content.categoryRecycleView.adapter = adapter
    }

    private fun initViewPager(){
        val adapter = offerViewPagerAdapter
        binding.content.offerViewPager.adapter = adapter
    }

    private fun startAutoScroll() {
        lifecycleScope.launch {
            while (true) {
                delay(3000) // Change delay duration as needed
                val offerViewPager = binding.content.offerViewPager
                val currentItem = offerViewPager.currentItem
                val totalItems = offerViewPager.adapter?.itemCount ?: 0
                val nextItem = (currentItem + 1) % totalItems
                offerViewPager.currentItem = nextItem
            }
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}