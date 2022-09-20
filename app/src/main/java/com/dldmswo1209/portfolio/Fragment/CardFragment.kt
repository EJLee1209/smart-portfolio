package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.adapter.CardListAdapter
import com.dldmswo1209.portfolio.databinding.FragmentCardBinding

class CardFragment : Fragment() {
    private lateinit var binding : FragmentCardBinding
    private val cardListAdapter = CardListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)

        binding.cardRecyclerView.adapter = cardListAdapter
        return binding.root
    }


}