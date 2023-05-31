package com.example.eitheror.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.eitheror.R
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.databinding.ActivityQuizBinding
import com.example.eitheror.ui.base.BaseActivity
import kotlinx.coroutines.launch

class QuizActivity : BaseActivity() {
    private var _binding: ActivityQuizBinding? = null
    private val binding get() = _binding!!

    var quiz: Quiz? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initQuiz()
        initClickListener()
    }

    private fun initQuiz() {

        val quizType = intent.getStringExtra("quizType")

        if (quizType == "RANDOM") {
            lifecycleScope.launch {
                val randomQuizResponse = api.getRandomQuiz()
                if (randomQuizResponse.isSuccessful) {
                    quiz = randomQuizResponse.body()!!
                    setQuizText(quiz)
                    initBottomSheetDialog(quiz)
                }
            }

        } else if (quizType == "SELECTED") {
            val quizId = intent.getIntExtra("id", 0)
            Log.i("quizId", quizId.toString())
            lifecycleScope.launch {
                val quizResponse = api.getQuiz(quizId)
                if (quizResponse.isSuccessful) {
                    quiz = quizResponse.body()!!
                    Log.i("quiz", quiz.toString())
                    setQuizText(quiz)
                    initBottomSheetDialog(quiz)
                }
            }
        }

    }


    private fun setQuizText(quiz: Quiz?) {

        binding.aQuizTitleTv.text = quiz!!.name
        binding.aQuizChoice1Btn.text = quiz!!.choice1
        binding.aQuizChoice2Btn.text = quiz!!.choice2

    }

    private fun initBottomSheetDialog(quiz: Quiz?) {
        binding.aQuizChatGptResponseBtn.setOnClickListener {
            val bottomSheetFragment = QuizBottomSheetFragment(quiz!!.chatGPTComment)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

        }

    }

    private fun initClickListener() {
        binding.aQuizChoice1Btn.setOnClickListener {
            lifecycleScope.launch {
                if (quiz != null) {
                    val choiceResponse = api.registerUserChoice(quiz!!.id!!, 1)
                    if (choiceResponse.isSuccessful){
                        Log.i("choice", choiceResponse.body()!!)
                        binding.aQuizChoice1Btn.text = String.format(getString(R.string.quiz_choice1_percentage), quiz!!.choice1, quiz!!.choice1SelectionNum!! + 1)
                        binding.aQuizChoice2Btn.text = String.format(getString(R.string.quiz_choice2_percentage), quiz!!.choice2, quiz!!.choice2SelectionNum)
                        binding.aQuizChatGptResponseBtn.visibility = View.VISIBLE
                        binding.aQuizChoice1Btn.isEnabled = false
                        binding.aQuizChoice2Btn.isEnabled = false
                    }
                }
            }
        }

        binding.aQuizChoice2Btn.setOnClickListener {
            lifecycleScope.launch {
                if (quiz != null) {
                    val choiceResponse = api.registerUserChoice(quiz!!.id!!, 2)
                    if (choiceResponse.isSuccessful){
                        Log.i("choice", choiceResponse.body()!!)
                        binding.aQuizChoice1Btn.text = String.format(getString(R.string.quiz_choice1_percentage), quiz!!.choice1, quiz!!.choice1SelectionNum)
                        binding.aQuizChoice2Btn.text = String.format(getString(R.string.quiz_choice2_percentage), quiz!!.choice2, quiz!!.choice2SelectionNum!! + 1)
                        binding.aQuizChatGptResponseBtn.visibility = View.VISIBLE
                        binding.aQuizChoice1Btn.isEnabled = false
                        binding.aQuizChoice2Btn.isEnabled = false

                    }
                }
            }
        }
    }


}