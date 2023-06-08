package com.example.orango.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.data.repo.RepoImpl
import com.example.data.roomDB.OranGoDataBase
import com.example.orango.R
import com.example.orango.databinding.CardOffersInHomeScreenBinding
import com.example.orango.util.PRODUCT_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfferViewPager : Fragment(){
    private var _binding: CardOffersInHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val database by lazy { OranGoDataBase.getInstance(requireContext()) }
    private val repo by lazy {   RepoImpl(database)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  CardOffersInHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = requireArguments()

        val productId = arguments.getInt(PRODUCT_ID)
        lifecycleScope.launch(Dispatchers.IO) {
            val product = repo.getProducts(productId)

            withContext(Dispatchers.Main) {
                binding.productNameOfferCard.text = product.productName
                Glide.with(requireContext())
                    .load(product.image)
                    .centerCrop()
                    .apply(RequestOptions().override(1600, 1600))
                    .placeholder(R.drawable.tomato)
                    .error(R.drawable.facebook)
                    .into(binding.productImg)
                binding.offerPercentage.text = product.offerValue.toString()
                binding.priceAfterDicount.text = product.price.toString()
            }
        }
    }
}