package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.dldmswo1209.portfolio.AddDialog
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.adapter.TimeLineAdapter
import com.dldmswo1209.portfolio.databinding.FragmentTimeLineBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel

class TimeLineFragment : Fragment() {

    private lateinit var binding: FragmentTimeLineBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val timeLineAdapter = TimeLineAdapter()
    private var uid = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = (activity as MainActivity).uid

        binding.timeLineRecyclerView.adapter = timeLineAdapter

        viewModel.getTimeLine(uid).observe(viewLifecycleOwner){
            timeLineAdapter.submitList(it)
        }

        binding.addButton.setOnClickListener{
            val dlg = AddDialog((activity as MainActivity))
            dlg.show{ timeLine->
                viewModel.createTimeLine(uid, timeLine)
            }
        }

    }


}