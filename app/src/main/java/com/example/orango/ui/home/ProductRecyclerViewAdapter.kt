package com.example.orango.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.data.roomDB.entities.ProductEntity
import com.example.orango.R
import com.example.orango.databinding.CardBestSellingInHomeScreenBinding
import com.example.orango.databinding.CardProductBinding

class ProductRecyclerViewAdapter(
    private val products: MutableList<ProductEntity>,
    private val cardNumber: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ProductViewHolder(private val binding: CardBestSellingInHomeScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductEntity) {
            binding.productNameInBestselling.text = product.productName
            binding.productPrice.text = product.price.toString()
            Glide.with(binding.root.context)
                .load(product.image)
                .centerCrop()
                .apply(RequestOptions().override(1600, 1600))
                .placeholder(R.drawable.tomato)
                .into(binding.productImgInBestselling)
        }
    }

    inner class FavouriteViewHolder(private val binding: CardProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductEntity) {
            binding.favouriteIcon.setImageResource(R.drawable.heart_icon)
            binding.productName.text = product.productName
            binding.productLocation.text = product.location
            binding.priceBeforeDicount.text = product.price.toString()
            binding.priceAfterDicount.text = (product.price - (product.price * (product.offerValue / 100f))).toString()
            Glide.with(binding.root.context)
                .load(product.image)
                .centerCrop()
                .placeholder(R.drawable.tomato)
                .into(binding.productImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (cardNumber) {
            1 -> {
                val binding = CardBestSellingInHomeScreenBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ProductViewHolder(binding)
            }

            else -> {
                val binding = CardProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FavouriteViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        when (cardNumber) {
            1 -> (holder as ProductViewHolder).bind(product)
            else -> (holder as FavouriteViewHolder).bind(product)
        }
    }

    fun updateList(products: List<ProductEntity>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }
}