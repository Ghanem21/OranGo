package com.example.orango.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.data.roomDB.entities.ProductEntity
import com.example.orango.R
import com.example.orango.databinding.ItemCartCardBinding

class CartAdapter (val products : MutableList<ProductEntity>,val quantities: MutableList<Int>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    //private val totalPriceLiveData
    inner class ViewHolder(private val binding: ItemCartCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductEntity,quantity: Int) {
            binding.productNameText.text = product.productName
            binding.productPriceText.text ="L.E${product.price} per each"
            binding.quantityText.text = quantity.toString()
            binding.totalPriceText.text = "L.E${product.price * quantity}"
            Glide.with(binding.root)
                .load(product.image)
                .placeholder(R.drawable.tomato)
                .centerCrop()
                .apply(RequestOptions.overrideOf(1600,1600).timeout(6000))
                .into(binding.productImg)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.ViewHolder {
        val binding = ItemCartCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        if(position < products.size && position < quantities.size) {
            val product = products[position]
            val quantity = quantities[position]
            holder.bind(product, quantity)
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateList(products: MutableList<ProductEntity>, quantities: MutableList<Int>){
        this.products.clear()
        this.quantities.clear()
        this.products.addAll(products)
        this.quantities.addAll(quantities)
        notifyDataSetChanged()
    }
}