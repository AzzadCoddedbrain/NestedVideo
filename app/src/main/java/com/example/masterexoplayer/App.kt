package com.example.masterexoplayer

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)

    }
}