package com.example.orango.ui.receiptHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.roomDB.entities.ReceiptEntity
import com.example.orango.databinding.CardReceiptHistoryBinding

class ReceiptHistoryAdapter(private val receipts : MutableList<ReceiptEntity>) : RecyclerView.Adapter<ReceiptHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardReceiptHistoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(receipt: ReceiptEntity) {
            binding.receiptNumber.text = receipt.receipt_number.toString()
            binding.receiptDate.text = receipt.date
            binding.receiptTotalPrice.text = receipt.total_price.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReceiptHistoryAdapter.ViewHolder {
        val binding = CardReceiptHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceiptHistoryAdapter.ViewHolder, position: Int) {
        val receipt = receipts[position]
        holder.bind(receipt)
    }

    override fun getItemCount(): Int = receipts.size

    fun updateList(receipts:List<ReceiptEntity>){
        this.receipts.clear()
        this.receipts.addAll(receipts)
        notifyDataSetChanged()
    }
}