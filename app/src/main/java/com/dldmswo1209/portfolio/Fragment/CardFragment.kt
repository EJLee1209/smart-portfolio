package com.dldmswo1209.portfolio.Fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.WebViewActivity
import com.dldmswo1209.portfolio.adapter.CardListAdapter
import com.dldmswo1209.portfolio.adapter.CardListAdapter.Companion.EDIT_CARD
import com.dldmswo1209.portfolio.adapter.CardListAdapter.Companion.GO_HOMEPAGE
import com.dldmswo1209.portfolio.databinding.FragmentCardBinding
import com.dldmswo1209.portfolio.swipeHelper.ItemDragHelperCallback
import com.dldmswo1209.portfolio.viewModel.MainViewModel

class CardFragment : Fragment(R.layout.fragment_card) {
    private lateinit var binding : FragmentCardBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var uid = ""
    private var isSuperShow = false

    val cardAdapter = CardListAdapter { card, type ->
        // 어답터를 생성할 때 아이템 클릭 이벤트를 정의함
        if(type == GO_HOMEPAGE) {
            // 아이템 클릭시 다이얼로그를 띄워 웹을 띄울 방법(내부/외부)을 선택
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("홈페이지 이동")
                .setMessage("내부 웹뷰 또는 외부 웹 브라우저를 선택해주세요.")
                .setPositiveButton("내부", DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(requireContext(), WebViewActivity::class.java)
                    intent.putExtra("url", card.link)
                    startActivity(intent)
                })
                .setNegativeButton("외부", DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(card.link))
                    startActivity(intent)
                })
            builder.show()
        }
        else if(type == EDIT_CARD){
            // 수정하기
            if(isSuperShow){ // 채용 담당자가 보는 중
                Toast.makeText(requireContext(), "권한이 없습니다.", Toast.LENGTH_SHORT).show()
                return@CardListAdapter
            }
            val bottomSheet = AddPortfolioBottomSheet(card)
            bottomSheet.show((activity as MainActivity).supportFragmentManager, bottomSheet.tag)
        }else{
            // 삭제하기
            if(isSuperShow){ // 채용 담당자가 보는 중
                Toast.makeText(requireContext(), "권한이 없습니다.", Toast.LENGTH_SHORT).show()
                return@CardListAdapter
            }
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("포트폴리오 삭제")
                .setMessage("삭제 하시겠습니까?")
                .setPositiveButton("삭제", DialogInterface.OnClickListener { dialog, id ->
                    viewModel.deleteCard(uid, card)
                    dialog.dismiss()
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
            builder.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = (activity as MainActivity).uid
        isSuperShow = (activity as MainActivity).isSuperShow

        if(isSuperShow){ // 채용 담당자가 보는 중
            binding.addButton.visibility = View.GONE
        }

        // 카드 드래그 기능을 위해 콜백 연결
        val callback = ItemDragHelperCallback(cardAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.cardRecyclerView)
        cardAdapter.startDrag(object: CardListAdapter.OnStartDragListener{
            override fun onStartDrag(viewHolder: CardListAdapter.ViewHolder) {
                touchHelper.startDrag(viewHolder)
            }

        })

        binding.cardRecyclerView.adapter = cardAdapter
        // 모든 카드리스트 데이터 요청
        viewModel.getCard(uid).observe(viewLifecycleOwner){
            cardAdapter.submitList(it)
            Log.d("testt", "observe : ${it.size}")
        }


        binding.addButton.setOnClickListener {
            val bottomSheet = AddPortfolioBottomSheet()
            bottomSheet.show((activity as MainActivity).supportFragmentManager, bottomSheet.tag)
        }

    }


}