package com.example.orango.ui.setting

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.orango.R
import com.example.orango.data.models.SettingAndServicesOption
import com.example.orango.databinding.ItemListServicesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
                    "Favourites" -> binding.root.findNavController().navigate(R.id.favouritesFragment)
                    "Points" -> binding.root.findNavController().navigate(R.id.pointFragment2)
                    "Receipts History" -> binding.root.findNavController().navigate(R.id.receiptHistoryFragment)
                    "Payment Details" -> binding.root.findNavController().navigate(R.id.paymentDetailsFragment)
                    "Contact Us" -> binding.root.findNavController().navigate(R.id.contactUsFragment)
                    "About Us" -> binding.root.findNavController().navigate(R.id.aboutUsFragment)
                    "Logout" -> {
                        MaterialAlertDialogBuilder(binding.root.context)
                            .setTitle("Logout Confirmation")
                            .setMessage("You sure, that you want to logout?")
                            .setIcon(R.drawable.logout)
                            .setNegativeButton("Cancel") { dialog, which ->
                                dialog.dismiss()
                            }
                            .setPositiveButton("Logout") { dialog, which ->
                                // Respond to positive button press
                            }
                            .create().apply {
                                getButton(DialogInterface.BUTTON_NEGATIVE)?.let{
                                    it.setTextColor(ContextCompat.getColor(binding.root.context,R.color.gray))
                                }
                                show()
                            }
                    }
                    "Delete Account" -> binding.root.findNavController().navigate(R.id.favouritesFragment)
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