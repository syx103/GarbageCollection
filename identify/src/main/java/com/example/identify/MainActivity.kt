package com.example.identify

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.BaseActivity
import com.example.identify.fragment.HomePageFragment
import com.example.identify.fragment.QuestionFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val beginTransient = supportFragmentManager.beginTransaction()
        beginTransient.add(R.id.identify_fragment, HomePageFragment(R.id.identify_fragment))
            .addToBackStack(null)
            .commit()
    }
}