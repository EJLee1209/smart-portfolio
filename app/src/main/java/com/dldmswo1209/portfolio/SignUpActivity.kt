package com.dldmswo1209.portfolio

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.databinding.ActivitySignUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    val superCodeList = mutableListOf(
        "super03245",
        "super09123",
        "super92193",
        "super72143",
        "super82137"
    )
    private var passwordIsSame = false
    private var isSuperUser = false
    private val auth = Firebase.auth
    private var dbRef = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 사용 목적 라디오 그룹 체크 변경 이벤트 처리
        // 관리자 코드 입력창을 보여줄지 안보여줄지 결정
        binding.purposeRadioGroup.setOnCheckedChangeListener { p0, radioId ->
            when(radioId){
                R.id.normalUser->{
                    // 일반 사용자
                    binding.superCodeLayout.visibility = View.GONE
                    isSuperUser = false
                }
                R.id.superUser->{
                    // 채용 담당자
                    binding.superCodeLayout.visibility = View.VISIBLE
                    isSuperUser = true

                }
            }
        }

        binding.passwordEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(password: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordIsSame = password.toString() == binding.passwordConfirmEditText.text.toString()
                if(passwordIsSame) {
                    binding.confirmTextView.text = "비밀번호가 일치합니다."
                    binding.confirmTextView.setTextColor(resources.getColor(R.color.main_blue, theme))
                }else{
                    binding.confirmTextView.text = "비밀번호가 일치하지 않습니다.."
                    binding.confirmTextView.setTextColor(resources.getColor(R.color.red, theme))
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        binding.passwordConfirmEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(confirmPassword: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordIsSame = confirmPassword.toString() == binding.passwordEditText.text.toString()
                if(passwordIsSame) {
                    binding.confirmTextView.text = "비밀번호가 일치합니다."
                    binding.confirmTextView.setTextColor(resources.getColor(R.color.main_blue, theme))
                }else{
                    binding.confirmTextView.text = "비밀번호가 일치하지 않습니다.."
                    binding.confirmTextView.setTextColor(resources.getColor(R.color.red, theme))
                }
            }

            override fun afterTextChanged(p0: Editable?) {}



        })

        binding.registerButton.setOnClickListener {
            // 회원가입
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val name = binding.nameTextView.text.toString()
            if(email == "" || password == "" || name == "" || !passwordIsSame){
                Toast.makeText(this, "입력 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!isSuperUser){ // 일반 사용자
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            // Realtime DB 에 사용자 정보 저장
                            val uid = auth.currentUser!!.uid
                            val newUser = User(uid, email, password, name, false)
                            dbRef.child("User/${uid}").setValue(newUser)

                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                    }


            }else{ // 채용 담당자
                val superCode = binding.superCodeEditText.text.toString()
                var isSuperCodeOk = false
                superCodeList.forEach {
                    if(superCode == it){
                        isSuperCodeOk = true
                        return@forEach
                    }
                }
                if(!isSuperCodeOk){
                    Toast.makeText(this, "잘못된 코드 입니다 개발자에게 문의해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            // Realtime DB 에 사용자 정보 저장
                            val uid = auth.currentUser!!.uid
                            val newUser = User(uid, email, password, name, true)
                            dbRef.child("User/${uid}").setValue(newUser)
                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                            Log.d("testt", it.exception.toString())
                        }
                    }

            }

        }
    }


}
