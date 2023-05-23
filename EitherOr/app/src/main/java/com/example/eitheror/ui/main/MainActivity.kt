package com.example.eitheror.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.eitheror.R
import com.example.eitheror.databinding.ActivityMainBinding
import com.example.eitheror.ui.addquiz.AddQuizActivity
import com.example.eitheror.ui.base.BaseActivity
import com.example.eitheror.ui.home.HomeFragment
import com.example.eitheror.ui.like.LikeFragment

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    private var exitMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigationView()
        initFloatingActionButton()
    }

    private fun initFloatingActionButton() {
        binding.fab.setOnClickListener {
            startActivity(Intent(applicationContext,AddQuizActivity()::class.java))
        }
    }

    private fun initBottomNavigationView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .addToBackStack(null)
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.like -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LikeFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        binding.mainBnv.itemIconTintList = null


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = supportFragmentManager.findFragmentById(R.id.main_frm)
                if (fragment is HomeFragment) {
                    killApp()
                }else{
                    supportFragmentManager.popBackStack()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

    }

    private fun killApp() {
        if (System.currentTimeMillis() - exitMillis > 2000) {
            exitMillis = System.currentTimeMillis()
            Toast.makeText(applicationContext, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
                .show()
        } else {
            finish()
        }

    }
}