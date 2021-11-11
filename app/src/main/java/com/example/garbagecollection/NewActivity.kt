package com.example.garbagecollection

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.base.BaseActivity

class NewActivity : BaseActivity() {
    private lateinit var newPhoneNumber: PhoneView
    private lateinit var newMyPassword: EditText
    private lateinit var newSure :EditText
    private lateinit var newOk :Button

    private lateinit var newViewModel: NewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        newViewModel = ViewModelProvider(this).get(NewViewModel::class.java)
        initView()
    }

    private fun initView(){
        newPhoneNumber = findViewById(R.id.newPhone)
        newMyPassword = findViewById(R.id.newPassword)
        newSure = findViewById(R.id.new_sure)
        newOk = findViewById(R.id.new_ok)

        newOk.setOnClickListener {
            newViewModel.phoneNumber = MutableLiveData(newPhoneNumber.text.toString())
            newViewModel.password= MutableLiveData(newMyPassword.text.toString())
            newViewModel.passwordSure = MutableLiveData(newSure.text.toString())
            if (newPhoneNumber.text == null || newPhoneNumber.text!!.length < 13) {
                Toast.makeText(this,"手机号不符合格式",Toast.LENGTH_SHORT).show()
            }
            if (newMyPassword.text.isBlank()) {
                Toast.makeText(this,"密码为空请重新输入",Toast.LENGTH_SHORT).show()
            }
            if (newMyPassword.text.toString() != newSure.text.toString()) {
                Toast.makeText(this,"确认密码不相符，请重新输入",Toast.LENGTH_SHORT).show()
            }
            if (newMyPassword.text.toString() == newSure.text.toString()) {
                //检查是否保存过
                    val prefs = getSharedPreferences("data",Context.MODE_PRIVATE)
                val phone = prefs.getString("phone","")
                if (phone == newPhoneNumber.text.toString())
                    Toast.makeText(this,"该手机号已经被注册,直接登录即可",Toast.LENGTH_SHORT).show()
                else {
                    savePhone()
                    Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    }

    private fun savePhone() {
        val editor = getSharedPreferences("data",Context.MODE_PRIVATE).edit()
        editor.putString("phone",newPhoneNumber.text.toString())
        editor.putString("password",newMyPassword.text.toString())
        editor.apply()
    }
}