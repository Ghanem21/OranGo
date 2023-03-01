package com.example.orango.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orango.data.models.Product
import com.example.orango.databinding.CardBestSellingInHomeScreenBinding

class ProductRecyclerViewAdapter(private val products : ArrayList<Product>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CardBestSellingInHomeScreenBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.productPrice.text = product.price.toString()
            binding.productNameInBestselling.text = product.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardBestSellingInHomeScreenBinding.inflate(
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