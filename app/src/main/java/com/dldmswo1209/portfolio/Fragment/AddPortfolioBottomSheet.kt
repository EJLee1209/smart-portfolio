package com.dldmswo1209.portfolio.Fragment


import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.Model.Card
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentAddPortfolioBottomSheetBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

// CardFragment 에서 addButton 을 눌렀을 때 나타나는 BottomSheetDialog
// 카드 포트폴리오를 추가하는 기능을 담고 있습니다.
class AddPortfolioBottomSheet(val card: Card? = null) : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentAddPortfolioBottomSheetBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var imageUri: Uri? = null
    private var uid = ""

    companion object{
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }
    // 새로 추가하는 카드인지 수정되는 카드인지 확인하기 위한 플래그
    private var isUpdate = false

    private var editCardId = "" // 수정되는 카드일 경우 이 변수에 카드의 key 값을 저장

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
    ): View {
        binding = FragmentAddPortfolioBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = (activity as MainActivity).uid

        if(card != null){ // 카드 수정하기를 누른 경우
            isUpdate = true // 플래그 on
            editCardId = card.key // 수정할 카드의 id 를 저장
            // 수정하는 상황에 맞게 ui를 바꾸기
            binding.titleTextView.text = "포트폴리오 수정하기"
            binding.addButton.text = "수정"
            Glide.with(this)
                .load(card.image?.toUri())
                .centerCrop()
                .into(binding.addImage)
            binding.titleEditText.setText(card.title)
            binding.linkEditText.setText(card.link)
            binding.contentEditText.setText(card.content)
            if(card.start != null && card.end != null){
                binding.startDateTextView.text = card.start
                binding.endDateTextView.text = card.end
            }
            imageUri = card.imageUri?.toUri()
        }
        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.addImage.setOnClickListener {
            selectGallery()
        }

        val currentDate = LocalDate.now().toString()
        val splitDate = currentDate.split("-")
        val year = splitDate[0].toInt()
        val month = splitDate[1].toInt() - 1
        val day = splitDate[2].toInt()
        binding.startDateTextView.setOnClickListener {
            DatePickerDialog(requireContext(),
                {   p0, year, month, day ->
                    val date = "$year-${String.format("%02d",month+1)}-${String.format("%02d",day)}"
                    binding.startDateTextView.text = date
                }, year, month, day).show()
        }

        binding.endDateTextView.setOnClickListener {
            DatePickerDialog(requireContext(),
                {   p0, year, month, day ->
                    val date = "$year-${String.format("%02d",month+1)}-${String.format("%02d",day)}"
                    binding.endDateTextView.text = date
                }, year, month, day).show()
        }

        binding.addButton.setOnClickListener {
            // 아이템 추가시 이미지는 없어도 되지만, 제목과 내용은 있어야 함
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val link = binding.linkEditText.text.toString()
            var start : String? = if(binding.startDateTextView.text.toString() == ""){
                null
            }else{
                binding.startDateTextView.text.toString()
            }
            var end : String? = if(binding.endDateTextView.text.toString() == ""){
                null
            }else{
                binding.endDateTextView.text.toString()
            }

            if(title == "" || content == "") return@setOnClickListener


            if(isUpdate){ // 수정하는 경우
                var newCard : Card
                if (imageUri == null) {
                    newCard = Card(title, content, card!!.key, card.image, null, link, start, end)
                } else {
                    newCard = Card(title, content, card!!.key , card.image, imageUri.toString(), link, start, end)
                }
                viewModel.updateCard(uid, newCard)
            }else { // 새로 추가하는 경우
                // 위 과정을 통과하면 Card 를 생성
                var newCard : Card
                if (imageUri == null) {
                    newCard = Card(title, content,"", null, null, link, start, end)

                } else {
                    newCard = Card(title, content,"", null, imageUri.toString(), link, start, end)
                }
                viewModel.createCard(uid, newCard)
            }
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