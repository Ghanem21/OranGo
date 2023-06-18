package com.example.orango.ui.bestSelling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.roomDB.entities.ProductEntity
import com.example.orango.R
import com.example.orango.databinding.CardBestSellingBinding

class BestSellingAdapter(private val products : MutableList<ProductEntity>) : RecyclerView.Adapter<BestSellingAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CardBestSellingBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductEntity) {
            binding.productPrice.text =  binding.root.context.getString(R.string.product_price_before_discount , (product.price - (product.price * (product.offerValue / 100f))).toString())
            binding.productNameInBestselling.text = product.productName
            binding.productLocation.text = product.location
            Glide.with(binding.root.context)
                .load(product.image)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_img)
                .into(binding.productImgInBestselling)

            binding.root.setOnClickListener {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.productFragment, bundleOf("productId" to product.id))
            }
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

    fun updateList(products: List<ProductEntity>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }
}