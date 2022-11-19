package com.dldmswo1209.portfolio

import android.app.DatePickerDialog
import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.dldmswo1209.portfolio.Model.TimeLine
import com.dldmswo1209.portfolio.databinding.AddDialogBinding
import java.time.LocalDate

// 타임라인 추가버튼을 누르면 뜨는 커스텀 다이얼로그
class AddDialog(private val context: AppCompatActivity){
    private val binding by lazy{
        AddDialogBinding.inflate(context.layoutInflater)
    }
    private val dialog = Dialog(context)

    // 다이얼로그를 보여주는 함수
    fun show(addClick: (TimeLine)->(Unit)){
        // 현재 날짜 가져오기
        val currentDate = LocalDate.now().toString().split("-")
        val year = currentDate[0].toInt()
        val month = currentDate[1].toInt()-1
        val day = currentDate[2].toInt()

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        // 날짜 textView 를 클릭했을 때
        binding.dateTextView.setOnClickListener {
            DatePickerDialog(context,
                { p0, year, month, day ->
                    val date = "$year-${month+1}-$day"
                    binding.dateTextView.text = date
                }, year, month, day).show()
        }

        // 추가를 눌렀을 때
        binding.addButton.setOnClickListener {
            val content = binding.contentEditText.text.toString()
            val date = binding.dateTextView.text.toString()
            if(content == "" || date == "") return@setOnClickListener // 내용이 비어있는 경우 추가하지 않음
            val newItem = TimeLine(content, date)
            addClick(newItem)

            dialog.dismiss() // 다이얼로그 끄기
        }

        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show() // 다이얼로그 보여줘
    }

}