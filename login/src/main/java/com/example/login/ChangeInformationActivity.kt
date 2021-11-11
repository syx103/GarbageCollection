package com.example.login

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.base.BaseActivity

class ChangeInformationActivity : BaseActivity() {

    private lateinit var changeName :EditText
    private lateinit var changeContent :EditText
    private lateinit var selectSex : RadioGroup
    private lateinit var back :ImageView
    private lateinit var changeSave : TextView

    private lateinit var informationViewModel :InformationViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_information)
        informationViewModel = ViewModelProvider(this).get(InformationViewModel::class.java)
        initView()
    }
    private fun initView(){
        changeName = findViewById(R.id.detail_change_name)
        changeContent = findViewById(R.id.detail_change_content)
        selectSex = findViewById(R.id.select_sex)
        back = findViewById(R.id.detail_back)
        changeSave = findViewById(R.id.change_save)

        var sex  = "女"


        selectSex.setOnCheckedChangeListener { group, checkedId ->
            val index = group.checkedRadioButtonId
            val id = findViewById<RadioButton>(index)
            sex = id.text.toString()
        }
        back.setOnClickListener {
            finish()
        }

        changeSave.setOnClickListener {
            informationViewModel.content = MutableLiveData(changeContent.text.toString())
            informationViewModel.name = MutableLiveData(changeName.text.toString())
            informationViewModel.sex = MutableLiveData(sex)

            saveInformation(sex)
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun saveInformation(sex :String) {
        val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        editor.putString("name",changeName.text.toString())
        editor.putString("content",changeContent.text.toString())
        editor.putString("sex",sex)
        editor.apply()
    }
}