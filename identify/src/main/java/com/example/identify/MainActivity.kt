package com.example.identify

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.BaseActivity
import com.example.base.HomePageFragmentPath
import com.example.identify.fragment.HomePageFragment
import com.example.identify.fragment.QuestionFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = ARouter.getInstance().build(HomePageFragmentPath).withInt("frameId",R.id.identify_fragment).navigation() as Fragment
        val beginTransient = supportFragmentManager.beginTransaction()
        beginTransient.add(R.id.identify_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}