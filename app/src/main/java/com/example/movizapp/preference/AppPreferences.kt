package com.example.movizapp.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object AppPreferences {
    private const val NAME = "property-userapp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
        Log.e("AppPreferences","init")

    }
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var chooseLanguage: String?
        get() = preferences.getString(ConstantPreference.LANGUAGE, "")
        set(value) = preferences.edit {
            it.putString(ConstantPreference.LANGUAGE, value!!)
        }
    var isLogin: Boolean
        get() = preferences.getBoolean(ConstantPreference.IS_LOGIN,false)
        set(value) = preferences.edit {
            it.putBoolean(ConstantPreference.IS_LOGIN,value)
        }
    var isFirstTime: Boolean
        get() = preferences.getBoolean(ConstantPreference.IS_FIRST_TIME,true)
        set(value) = preferences.edit {
            it.putBoolean(ConstantPreference.IS_FIRST_TIME,value)
        }
    var token: String?
        get() = preferences.getString(ConstantPreference.USER_TOKEN, "")
        set(value) = preferences.edit {
            it.putString(ConstantPreference.USER_TOKEN, value!!)
        }

    fun logoutClearPreference() {
        AppPreferences.isLogin = false
        AppPreferences.token = ""
        preferences.edit().clear()
        preferences.edit().commit()


    }
}