package com.example.orango.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.orango.R
import com.example.orango.databinding.CardOffersInHomeScreenBinding
import com.example.orango.databinding.FragmentViewPagerBinding
import com.example.orango.util.BODY
import com.example.orango.util.IMAGE_ID
import com.example.orango.util.PRODUCT_ID
import com.example.orango.util.TITLE

class OfferViewPager : Fragment(){
    private var _binding: CardOffersInHomeScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  CardOffersInHomeScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = requireArguments()

        val productId = arguments.getInt(PRODUCT_ID)


    }
}