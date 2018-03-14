package com.zhongwenhui.tdin360.kotlindemo

import android.app.Application
import com.zhongwenhui.tdin360.kotlindemo.extensions.DelegatesExt

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}