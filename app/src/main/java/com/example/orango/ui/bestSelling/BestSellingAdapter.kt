package com.example.orango.ui.bestSelling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orango.data.models.Product
import com.example.orango.databinding.CardBestSellingBinding

class BestSellingAdapter(private val products : ArrayList<Product>) : RecyclerView.Adapter<BestSellingAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CardBestSellingBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.productPrice.text = product.price.toString()
            binding.productNameInBestselling.text = product.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardBestSellingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }
}