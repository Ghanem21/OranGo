package com.example.orango.ui.receiptHistory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orango.R

class ReceiptHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = ReceiptHistoryFragment()
    }

    private lateinit var viewModel: ReceiptHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_receipt_history, container, false)
    }


}