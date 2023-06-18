package com.example.orango.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.orango.R
import com.example.orango.data.DataManager
import com.example.orango.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val settingAndServiceAdapter by lazy {
        SettingAndServiceAdapter(DataManager.settings)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun setUpRecyclerView() {
        binding.servicesList.adapter = settingAndServiceAdapter
    }
}