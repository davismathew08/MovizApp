package com.example.movizapp.utils

import android.content.Context
import android.net.Uri
import android.os.SystemClock
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movizapp.R

object CommonMethods {
    var mLastClickTime=0L

    fun isOpenRecently():Boolean{
        Log.e("isOpenRecently","inside")
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }
     fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }
    fun getImageRealPath(uri : Uri?, context : Context) : String? {
        try {
            val imagePath : String
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(
                uri!! ,
                filePathColumn ,
                null ,
                null ,
                null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            imagePath = cursor.getString(columnIndex)
            cursor.close()
            return imagePath
        } catch (ex : Exception) {
            Log.e("123456" , "getImageRealPath: " + ex)
            return ""
        }

    }
}