package com.example.base

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(supportActionBar != null)
            supportActionBar!!.hide()
    }

    @SuppressLint("WrongConstant")  //忽略API版本警告
    @RequiresApi(Build.VERSION_CODES.R)    //指定版本号为30
    override fun onWindowFocusChanged(hasFocus: Boolean) {   //窗体得到或者失去焦点的时候进行调用,监控activity完成渲染
        super.onWindowFocusChanged(hasFocus)
        val controller = ViewCompat.getWindowInsetsController(window.decorView)
        controller?.hide(WindowInsets.Type.statusBars())
        controller?.isAppearanceLightNavigationBars = true //隐藏标题栏成功设置，成为亮色主题
    }
}