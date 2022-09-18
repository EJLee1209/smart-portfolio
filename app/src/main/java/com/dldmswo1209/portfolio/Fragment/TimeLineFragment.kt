package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentTimeLineBinding

class TimeLineFragment : Fragment() {
    private lateinit var binding: FragmentTimeLineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTimeLineBinding.inflate(inflater, container, false)
        return binding.root
    }


}