package com.example.eitheror.util

import android.content.Context

class PreferenceUtil(context:Context) {
    private val spf = context.getSharedPreferences("spf", Context.MODE_PRIVATE)

    fun getBoolean(key: String, value: Boolean): Boolean{
        return spf.getBoolean(key, value)
    }

    fun setBoolean(key: String, value: Boolean){
        spf.edit().putBoolean(key, value).apply()
    }

    fun setUserID(key: String, id: Int){
        spf.edit().putInt(key, id).apply()
    }
    fun getUserId(key:String): Int{
        return spf.getInt(key, 1)
    }



}