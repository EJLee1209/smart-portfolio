package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.adapter.CardListAdapter
import com.dldmswo1209.portfolio.databinding.FragmentCardBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel

class CardFragment : Fragment(R.layout.fragment_card) {
    private lateinit var binding : FragmentCardBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCardBinding.bind(view)
        val cardAdapter = CardListAdapter()

        // 모든 카드리스트 데이터 요청
        viewModel.getAllCard()

        viewModel.cardList.observe(viewLifecycleOwner, Observer {
            // cardList 의 데이터 변화를 관찰
            // getAllCard 에 의해서 최초 실행시 데이터 변경이 관찰 됨.
            Log.d("testt", it.toString())
            cardAdapter.submitList(it)
            binding.cardRecyclerView.adapter = cardAdapter
            cardAdapter.notifyDataSetChanged()
        })

        binding.addButton.setOnClickListener {
            val bottomSheet = AddPortfolioBottomSheet()
            bottomSheet.show((activity as MainActivity).supportFragmentManager, bottomSheet.tag)
        }

    }


}