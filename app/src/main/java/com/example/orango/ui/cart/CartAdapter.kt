package com.example.orango.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.data.roomDB.entities.ProductEntity
import com.example.orango.R
import com.example.orango.databinding.ItemCartCardBinding

class CartAdapter (val map: MutableMap<ProductEntity,Int>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
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
        if(position < map.size ) {
            val product = map.keys.toList()[position]
            val quantity = map.values.toList()[position]
            holder.bind(product, quantity)
        }
    }

    override fun getItemCount(): Int = map.size

    fun updateList(map: Map<ProductEntity,Int>){
        this.map.clear()
        this.map.putAll(map)
        notifyDataSetChanged()
    }

    fun getMap() : Map<String,Int>{
        val map = mutableMapOf<String,Int>()
        this.map.forEach {product ->
            map[product.key.id.toString()] = product.value
        }
        return map
    }

    fun getTotalPrice(): Float {
        var totalPrice = 0.0f
        this.map.forEach {
            val product = it.key
            val price = (product.price - (product.price * (product.offerValue / 100f)))
            totalPrice += (price * it.value)
        }
        return totalPrice
    }
}