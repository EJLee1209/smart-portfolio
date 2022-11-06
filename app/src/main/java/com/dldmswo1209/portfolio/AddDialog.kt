package com.dldmswo1209.portfolio

import android.app.DatePickerDialog
import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.dldmswo1209.portfolio.databinding.AddDialogBinding
import com.dldmswo1209.portfolio.entity.TimeLineEntity

class AddDialog(private val context: AppCompatActivity){
    private val binding by lazy{
        AddDialogBinding.inflate(context.layoutInflater)
    }
    private val dialog = Dialog(context)

    fun show(addClick: (TimeLineEntity)->(Unit)){

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        binding.dateTextView.setOnClickListener {
            DatePickerDialog(context,
                { p0, year, month, day ->
                    val date = "$year-${month+1}-$day"
                    binding.dateTextView.text = date
                }, 2022, 10, 6).show()
        }

        binding.addButton.setOnClickListener {
            val content = binding.contentEditText.text.toString()
            val date = binding.dateTextView.text.toString()
            val newItem = TimeLineEntity(0,content, date)
            addClick(newItem)

            dialog.dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}