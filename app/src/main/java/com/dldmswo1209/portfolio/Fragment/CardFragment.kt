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
    val cardAdapter = CardListAdapter { cardEntity, type ->
        // 어답터를 생성할 때 아이템 클릭 이벤트를 정의함
        if(type == GO_HOMEPAGE) {
            // 아이템 클릭시 다이얼로그를 띄워 웹을 띄울 방법(내부/외부)을 선택
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("홈페이지 이동")
                .setMessage("내부 웹뷰 또는 외부 웹 브라우저를 선택해주세요.")
                .setPositiveButton("내부", DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(requireContext(), WebViewActivity::class.java)
                    intent.putExtra("url", cardEntity.link)
                    startActivity(intent)
                })
                .setNegativeButton("외부", DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cardEntity.link))
                    startActivity(intent)
                })
            builder.show()
        }else if(type == EDIT_CARD){
            // 수정하기
            val bottomSheet = AddPortfolioBottomSheet(cardEntity)
            bottomSheet.show((activity as MainActivity).supportFragmentManager, bottomSheet.tag)
        }else{
            // 삭제하기
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("포트폴리오 삭제")
                .setMessage("삭제 하시겠습니까?")
                .setPositiveButton("삭제", DialogInterface.OnClickListener { dialog, id ->


                    dialog.dismiss()
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
            builder.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCardBinding.bind(view)


        // 카드 드래그 기능을 위해 콜백 연결
        val callback = ItemDragHelperCallback(cardAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.cardRecyclerView)
        cardAdapter.startDrag(object: CardListAdapter.OnStartDragListener{
            override fun onStartDrag(viewHolder: CardListAdapter.ViewHolder) {
                touchHelper.startDrag(viewHolder)
            }

        })

        // 모든 카드리스트 데이터 요청




        binding.addButton.setOnClickListener {
            val bottomSheet = AddPortfolioBottomSheet()
            bottomSheet.show((activity as MainActivity).supportFragmentManager, bottomSheet.tag)
        }

    }


}