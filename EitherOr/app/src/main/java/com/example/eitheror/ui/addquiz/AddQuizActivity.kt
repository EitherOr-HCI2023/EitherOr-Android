package com.example.eitheror.ui.addquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eitheror.databinding.ActivityAddQuizBinding

class AddQuizActivity : AppCompatActivity() {
    private var _binding: ActivityAddQuizBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}