package com.example.eitheror

import android.app.Application
import com.example.eitheror.Util.PreferenceUtil
import com.example.eitheror.api.ApiService

class App: Application() {

    companion object{
        lateinit var spf: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        ApiService.init(this)
        spf = PreferenceUtil(applicationContext)
    }
}