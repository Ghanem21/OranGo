package com.example.orango.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    private lateinit var binding: FragmentHomeBinding

    private val productBestSellingAdapter by lazy {
        ProductRecyclerViewAdapter(
            viewModel.bestSellingProductsTopFive.value?.toMutableList() ?: mutableListOf(), 1
        )
    }

    private val categoryAdapter by lazy {
        CategoryRecyclerViewAdapter(
            viewModel.categoriesTopFive.value?.toMutableList() ?: mutableListOf()
        )
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        setupAdapters()
        observeData()


        startAutoScroll()

        setOnClickListeners()
    }

    private fun setupAdapters() {
        binding.content.bestSellingRecyclerView.adapter = productBestSellingAdapter
        binding.content.categoryRecycleView.adapter = categoryAdapter
        try {
            binding.content.offerViewPager.adapter = offerViewPagerAdapter
            binding.content.circleIndicator3.setViewPager(binding.content.offerViewPager)
        } catch (ex: Exception) {
            Toast.makeText(requireContext(),"Bad internet Connection",Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }

    private fun observeData() {
        viewModel.bestSellingProductsTopFive.observe(viewLifecycleOwner) { products ->
            productBestSellingAdapter.updateList(products)
        }

        viewModel.categoriesTopFive.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.updateList(categories)
        }

        viewModel.offersTopFive.observe(viewLifecycleOwner) { offers ->
            offerViewPagerAdapter.updateList(initOfferFragments(offers))
            setupAdapters()
        }

        viewModel.savedCustomerData.observe(viewLifecycleOwner) { userData ->
            binding.headerLayout.usernameText.text = "Hi, ${userData.user.user_name}"
            Glide.with(requireContext())
                .load(userData.user.image)
                .centerCrop()
                .apply(RequestOptions().override(1600, 1600).timeout(6000))
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.profile)
                .into(binding.headerLayout.profileIcon)
        }
    }

    private fun initViewPager() {
        binding.content.offerViewPager.apply {
            val currentItem = currentItem
            val totalItems = adapter?.itemCount ?: 0
            val nextItem = if (totalItems != 0) (currentItem + 1) % totalItems else 0
            setCurrentItem(nextItem, true)
        }
    }

    private fun startAutoScroll() {
        lifecycleScope.launch {
            while (true) {
                delay(3000)
                val offerViewPager = binding.content.offerViewPager
                val currentItem = offerViewPager.currentItem
                val totalItems = offerViewPager.adapter?.itemCount ?: 0
                val nextItem = if (totalItems != 0) (currentItem + 1) % totalItems else 0
                offerViewPager.setCurrentItem(nextItem, true)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.content.bestSeeAllText.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bestSellingFragment)
        }

        binding.content.offerSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_offerFragment)
        }

        binding.content.categorySeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoriesScreenFragment)
        }

        binding.headerLayout.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.headerLayout.settingIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }

        binding.headerLayout.profileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_editProfileFragment2)
        }
    }

    private fun initOfferFragments(offerProducts: List<ProductEntity>): MutableList<Fragment> {
        val offerFragmentList = mutableListOf<Fragment>()
        for (product in offerProducts) {
            val bundle = Bundle()
            bundle.putInt(PRODUCT_ID, product.id)
            val fragment = OfferViewPager()
            fragment.arguments = bundle
            offerFragmentList.add(fragment)
        }
        return offerFragmentList
    }

    override fun onPause() {
        super.onPause()
        binding.content.offerViewPager.adapter = null
    }
}