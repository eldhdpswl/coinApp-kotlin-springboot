package com.example.coco

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {

    init {
        instance = this
    }
    
    // 편의성을 위해 설정
    companion object {
        private var instance : App? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}