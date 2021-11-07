package com.example.identify

import android.os.Bundle
import com.example.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val beginTransient = supportFragmentManager.beginTransaction()
        beginTransient.add(R.id.identify_fragment,HomePageFragment())
            .addToBackStack(null)
            .commit()
    }
}