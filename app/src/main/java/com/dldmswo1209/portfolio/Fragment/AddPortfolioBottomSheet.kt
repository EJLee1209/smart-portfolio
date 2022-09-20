package com.dldmswo1209.portfolio.Fragment


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentAddPortfolioBottomSheetBinding
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.selects.select
import java.util.jar.Manifest

class AddPortfolioBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentAddPortfolioBottomSheetBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var imageUri: Uri? = null

    companion object{
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }

    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if((result.resultCode == RESULT_OK)){
            // 이미지를 받으면 imageUri 변수에 저장 후 imageView 에 적용
            imageUri = result.data?.data

            imageUri.let {
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .apply(RequestOptions().override(500,500))
                    .into(binding.addImage)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add_portfolio_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPortfolioBottomSheetBinding.bind(view)

        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.addImage.setOnClickListener {
            selectGallery()
        }

        binding.addButton.setOnClickListener {
            // 아이템 추가시 이미지는 없어도 되지만, 제목과 내용은 있어야 함
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            if(title == "" || content == "") return@setOnClickListener

            // 위 과정을 통과하면 CardEntity 를 생성
            viewModel.insertCard(CardEntity(0,imageUri.toString(), title, content))
            dialog?.dismiss()
        }

    }
    // 갤러리를 부르는 메서드
    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED ||
                readPermission == PackageManager.PERMISSION_DENIED){
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions((activity as MainActivity),
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE), REQ_GALLERY)
        }else{ // 권한이 있는 경우
            // 갤러리 실행
            val intent = Intent(Intent.ACTION_PICK)
            // intent 의 data와 type 을 동시에 설정하기
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )

            imageResult.launch(intent)
        }

    }

}