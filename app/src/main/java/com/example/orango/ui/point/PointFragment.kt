package com.example.orango.ui.point

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.orango.databinding.FragmentPointBinding

class PointFragment : Fragment() {

    private val viewModel: PointViewModel by viewModels()
    private lateinit var binding: FragmentPointBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.points.observe(viewLifecycleOwner){ points->
            binding.numPoints.text = points.toString()
            binding.numMoney.text = (points / 10f).toString()
        }
    }

}