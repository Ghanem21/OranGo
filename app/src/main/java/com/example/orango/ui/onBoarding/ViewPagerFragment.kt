package com.example.orango.ui.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orango.R
import com.example.orango.databinding.FragmentOnBoardingBinding
import com.example.orango.databinding.FragmentViewPagerBinding
import com.example.orango.util.BODY
import com.example.orango.util.IMAGE_ID
import com.example.orango.util.TITLE


/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentViewPagerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = requireArguments()

        binding.imageView.setImageResource(arguments.getInt(IMAGE_ID,R.drawable.time_onboard))
        binding.title.text = getString(arguments.getInt(TITLE,R.string.no_more_long_queues))
        binding.body.text = getString(arguments.getInt(BODY,R.string.you_can_now_skip_long_queues_at_stores_while_shopping))

    }

}