package com.example.orango.ui.receiptNo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orango.R

class ReceiptNumberFragment : Fragment() {

    companion object {
        fun newInstance() = ReceiptNumberFragment()
    }

    private lateinit var viewModel: ReceiptNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_receipt_number, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReceiptNumberViewModel::class.java)
        // TODO: Use the ViewModel
    }

}