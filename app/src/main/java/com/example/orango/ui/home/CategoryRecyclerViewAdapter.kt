package com.example.orango.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.roomDB.entities.CategoryEntity
import com.example.orango.R
import com.example.orango.databinding.CardCategoriesBinding

class CategoryRecyclerViewAdapter(private val categories : MutableList<CategoryEntity>) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
    private val selectedCategoryLiveData = MutableLiveData<Int>()
    val selectedCategory : LiveData<Int> = selectedCategoryLiveData
    inner class ViewHolder(private val binding: CardCategoriesBinding) : RecyclerView.ViewHolder(binding.root){
        private var categoryId :Int = -1
        fun bind(category: CategoryEntity) {
            categoryId = category.id
            binding.categoryName.text = category.name
            Glide.with(binding.root.context)
                .load(category.image)
                .centerCrop()
                .placeholder(R.drawable.fruit)
                .into(binding.categoryImg)
        }

        init {
            binding.root.setOnClickListener {
                selectedCategoryLiveData.value = categoryId
                val navController = binding.root.findNavController()
                navController.navigate(R.id.categoriesScreenFragment, bundleOf("categoryId" to categoryId))
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

    fun updateList(categories: MutableList<CategoryEntity>){
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }
}