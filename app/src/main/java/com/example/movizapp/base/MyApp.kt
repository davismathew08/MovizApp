package com.example.movizapp.base

import android.app.Application
import com.example.movizapp.preference.AppPreferences

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }

}