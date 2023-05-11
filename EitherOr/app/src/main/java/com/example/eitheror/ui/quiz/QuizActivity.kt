package com.example.eitheror.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eitheror.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    private var _binding: ActivityQuizBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}