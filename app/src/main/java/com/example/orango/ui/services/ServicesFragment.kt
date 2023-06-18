package com.example.orango.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.data.DataManager
import com.example.orango.databinding.FragmentServicesBinding
import com.example.orango.ui.setting.SettingAndServiceAdapter

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    private val settingAndServiceAdapter by lazy {
        SettingAndServiceAdapter(DataManager.services)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.backImg.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun setUpRecyclerView() {
        binding.servicesList.adapter = settingAndServiceAdapter
    }
}