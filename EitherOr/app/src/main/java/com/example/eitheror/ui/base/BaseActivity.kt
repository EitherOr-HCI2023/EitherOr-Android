package com.example.eitheror.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.example.eitheror.api.ApiService

abstract class BaseActivity : AppCompatActivity() {
    val api: ApiService by lazy { ApiService.getInstance() }

}