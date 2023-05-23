package com.example.eitheror.ui.base

import androidx.fragment.app.Fragment
import com.example.eitheror.api.ApiService

abstract class BaseFragment : Fragment() {
    val api: ApiService by lazy { ApiService.getInstance() }

}