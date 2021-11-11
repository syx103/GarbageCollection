package com.example.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

open class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}