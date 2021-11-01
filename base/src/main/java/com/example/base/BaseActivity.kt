package com.example.base

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//            设置导航栏和状态栏为透明色
        window.navigationBarColor = Color.TRANSPARENT;
        window.statusBarColor = Color.TRANSPARENT;
        if(supportActionBar != null)
            supportActionBar!!.hide()
    }
}