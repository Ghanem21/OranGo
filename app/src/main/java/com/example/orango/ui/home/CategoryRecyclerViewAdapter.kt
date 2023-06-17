package com.example.orango.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
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
                .apply(RequestOptions().override(1600, 1600).timeout(6000))
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_img)
                .into(binding.categoryImg)

            binding.root.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCategoriesScreenFragment(categoryId = category.id)
                binding.root.findNavController().navigate(action)
            }
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