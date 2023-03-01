package com.example.orango.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orango.data.models.Category
import com.example.orango.databinding.CardCategoriesBinding

class CategoryRecyclerViewAdapter(private val categories : ArrayList<Category>) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CardCategoriesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category) {
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
}