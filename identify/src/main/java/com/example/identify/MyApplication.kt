package com.example.identify

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.BaseApplication

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.init(this)
    }
    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}