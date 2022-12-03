package com.dldmswo1209.portfolio

import android.app.DatePickerDialog
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.dldmswo1209.portfolio.Model.TimeLine
import com.dldmswo1209.portfolio.databinding.AddDialogBinding
import java.time.LocalDate

// 타임라인 추가버튼을 누르면 뜨는 커스텀 다이얼로그
class AddDialog(private val context: AppCompatActivity, private val timeLine: TimeLine? = null) : AdapterView.OnItemSelectedListener{
    private val binding by lazy{
        AddDialogBinding.inflate(context.layoutInflater)
    }
    private val dialog = Dialog(context)
    private var selectSubject = "Education"

    // 다이얼로그를 보여주는 함수
    fun show(addClick: (TimeLine)->(Unit)) {
        linkedSpinnerAdapter()

        if (timeLine != null) { // 타임라인 수정일 경우
            binding.titleTextView.text = "타임라인 수정하기"
            binding.dateTextView.text = timeLine.date
            binding.contentEditText.setText(timeLine.content)
            when(timeLine.subject){
                "Education"->{
                    binding.spinner.setSelection(0)
                }
                "Award"->{
                    binding.spinner.setSelection(1)
                }
                "Activity"->{
                    binding.spinner.setSelection(2)
                }

                "Project"->{
                    binding.spinner.setSelection(3)
                }
                "Etc"->{
                    binding.spinner.setSelection(4)
                }

            }
            binding.addButton.text = "수정"
        }

        // 현재 날짜 가져오기
        val currentDate = LocalDate.now().toString().split("-")
        val year = currentDate[0].toInt()
        val month = currentDate[1].toInt() - 1
        val day = currentDate[2].toInt()

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        // 날짜 textView 를 클릭했을 때
        binding.dateTextView.setOnClickListener {
            if (timeLine == null) { // 단순 추가
                DatePickerDialog(
                    context,
                    { p0, year, month, day ->
                        val date = "$year-${month + 1}-$day"
                        binding.dateTextView.text = date
                    }, year, month, day
                ).show()
            } else { // 타임라인 수정
                val savedDate = timeLine.date.split("-")
                val savedYear = savedDate[0].toInt()
                val savedMonth = savedDate[1].toInt() - 1
                val savedDay = savedDate[2].toInt()

                DatePickerDialog(
                    context,
                    { p0, year, month, day ->
                        val date = "$year-${month + 1}-$day"
                        binding.dateTextView.text = date
                    }, savedYear, savedMonth, savedDay
                ).show()
            }
        }

        // 추가를 눌렀을 때
        binding.addButton.setOnClickListener {
            val content = binding.contentEditText.text.toString()
            val date = binding.dateTextView.text.toString()
            if (content == "" || date == "" || selectSubject == "") return@setOnClickListener // 내용이 비어있는 경우 추가하지 않음
            val newItem = TimeLine(content, date, selectSubject)
            if (timeLine != null) { // 타임라인 수정인 경우
                newItem.key = timeLine.key
            }
            addClick(newItem)

            dialog.dismiss() // 다이얼로그 끄기
        }

        binding.cancelButton.setOnClickListener {
                dialog.dismiss()
        }

        dialog.show() // 다이얼로그 보여줘
    }

    private fun linkedSpinnerAdapter(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.subject_arr,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter

        }
        binding.spinner.onItemSelectedListener = this
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when(pos){
            0->{
                // Education
                selectSubject = "Education"
            }
            1->{
                // Award
                selectSubject = "Award"
            }
            2->{
                // Activity
                selectSubject = "Activity"
            }
            3->{
                // Project
                selectSubject = "Project"
            }
            4->{
                // Etc
                selectSubject = "Etc"
            }

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}


}