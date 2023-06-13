package com.example.orango.ui.receiptHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.orango.databinding.FragmentReceiptHistoryBinding

class ReceiptHistoryFragment : Fragment() {

    private val viewModel: ReceiptHistoryViewModel by viewModels()
    private lateinit var binding: FragmentReceiptHistoryBinding

    private val receiptHistoryAdapter by lazy {
        ReceiptHistoryAdapter(mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceiptHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        viewModel.receipts.observe(viewLifecycleOwner){receipts ->
            receiptHistoryAdapter.updateList(receipts)
        }
    }

    private fun setUpAdapter(){
        binding.receiptHistoryRecycleView.adapter = receiptHistoryAdapter
    }
}