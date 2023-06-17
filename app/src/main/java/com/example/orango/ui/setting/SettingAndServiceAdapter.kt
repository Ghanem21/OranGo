package com.example.orango.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.orango.R
import com.example.orango.data.models.SettingAndServicesOption
import com.example.orango.databinding.ItemListServicesBinding

class SettingAndServiceAdapter(private val options : List<SettingAndServicesOption>) : RecyclerView.Adapter<SettingAndServiceAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemListServicesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(option: SettingAndServicesOption) {
            binding.imgIcon.setImageResource(option.imgId)
            binding.title.text = option.name
        }

        init {
            binding.root.setOnClickListener {
                when (binding.title.text){
                    "Suggested Meals" -> binding.root.findNavController().navigate(R.id.suggestedMealsFragment)
                    "Notes" -> binding.root.findNavController().navigate(R.id.notesFragment)
                    else -> binding.root.findNavController().navigate(R.id.favouritesFragment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListServicesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = options.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = options[position]
        holder.bind(product)
    }
}