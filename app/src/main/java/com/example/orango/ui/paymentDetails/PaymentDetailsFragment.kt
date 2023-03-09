package com.example.orango.ui.paymentDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orango.R

class PaymentDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentDetailsFragment()
    }

    private lateinit var viewModel: PaymentDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}