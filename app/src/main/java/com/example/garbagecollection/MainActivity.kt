package com.example.garbagecollection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.base.BaseActivity

class MainActivity :  BaseActivity(){

    private lateinit var mainPhoneNumber: PhoneView
    private lateinit var mainMyPassword : EditText
    private lateinit var loginOther : TextView
    private lateinit var loginNew : TextView
    private lateinit var login :Button
    private lateinit var skip :TextView

    private lateinit var loginViewModel :LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initView()
    }

    private fun initView(){
        mainPhoneNumber = findViewById(R.id.phone_my_number)
        mainMyPassword = findViewById(R.id.password_my)
        loginOther = findViewById(R.id.login_other)
        loginNew = findViewById(R.id.login_new)
        login = findViewById(R.id.login_in)
        skip = findViewById(R.id.main_skip)

        loginNew.setOnClickListener {
            val intent = Intent(this,NewActivity::class.java)
            startActivity(intent)
        }

        loginOther.setOnClickListener {
            Toast.makeText(this,"暂不支持",Toast.LENGTH_SHORT).show()
        }

        login.setOnClickListener {
            loginViewModel.phoneNumber = MutableLiveData(mainPhoneNumber.text.toString())
            loginViewModel.password = MutableLiveData(mainMyPassword.text.toString())

            val preferences = getSharedPreferences("data",Context.MODE_PRIVATE)
            val phone = preferences.getString("phone","")
            val password = preferences.getString("password","")


            if (phone == mainPhoneNumber.text.toString() && password == mainMyPassword.text.toString()) {
                //跳转到主页面

            }else {
                Toast.makeText(this,"账号或密码不正确",Toast.LENGTH_SHORT).show()
            }
        }

        skip.setOnClickListener {
            //跳转主页面
        }

    }


}