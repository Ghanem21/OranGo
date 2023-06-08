package com.example.orango.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.data.roomDB.entities.CategoryEntity
import com.example.orango.R
import com.example.orango.databinding.CardCategoriesBinding

class CategoryRecyclerViewAdapter(private val categories : MutableList<CategoryEntity>) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CardCategoriesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: CategoryEntity) {
            binding.categoryName.text = category.name
            Glide.with(binding.root.context)
                .load(category.image)
                .centerCrop()
                .apply(RequestOptions().override(1600, 1600))
                .placeholder(R.drawable.tomato)
                .error(R.drawable.facebook)
                .into(binding.categoryImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardCategoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    fun updateList(categories: List<CategoryEntity>){
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }
}