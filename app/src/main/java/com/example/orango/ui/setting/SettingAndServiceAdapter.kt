package com.example.orango.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orango.databinding.ItemListServicesBinding

class SettingAndServiceAdapter(private val options : ArrayList<SettingAndServiceAdapter>) : RecyclerView.Adapter<SettingAndServiceAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemListServicesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(option: SettingAndServiceAdapter) {

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