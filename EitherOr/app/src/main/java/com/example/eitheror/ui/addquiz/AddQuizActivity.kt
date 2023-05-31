package com.example.eitheror.ui.addquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.eitheror.R
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.api.response.QuizData
import com.example.eitheror.databinding.ActivityAddQuizBinding
import com.example.eitheror.ui.base.BaseActivity
import com.example.eitheror.ui.quiz.QuizBottomSheetFragment
import kotlinx.coroutines.launch

class AddQuizActivity : BaseActivity() {
    private var _binding: ActivityAddQuizBinding? = null
    private val binding get() = _binding!!
    private var quizPassword = ""
    private var categories = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickListener()
    }

    private fun initClickListener() {

        binding.addQuizLogoTv.setOnClickListener {
            val bottomSheetFragment = AddQuizBottomSheetFragment()
            bottomSheetFragment.data.observe(this) { receivedData ->
                binding.currentCategory.text = receivedData.toString()
                categories = receivedData
            }
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        binding.addQuizTitleEt.setOnFocusChangeListener { view, b ->
            if (binding.addQuizTitleEt.hint.toString() != "") binding.addQuizTitleEt.hint = ""
            else binding.addQuizTitleEt.setHint(R.string.add_quiz_title)
        }

        binding.addQuizChoice1Et.setOnFocusChangeListener { view, b ->

            if (binding.addQuizChoice1Et.hint.toString() != "") binding.addQuizChoice1Et.hint = ""
            else binding.addQuizChoice1Et.setHint(R.string.add_quiz_choice1)
        }

        binding.addQuizChoice2Tv.setOnFocusChangeListener { view, b ->

            if (binding.addQuizChoice2Tv.hint.toString() != "") binding.addQuizChoice2Tv.hint = ""
            else binding.addQuizChoice2Tv.setHint(R.string.add_quiz_choice2)
        }

        binding.addQuizConfirmBtn.setOnClickListener {
            showPasswordDialog()
        }
    }

    private fun showPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        val inputView = View.inflate(this, R.layout.dialog_add_quiz_password, null)
        builder.setView(inputView)

        builder.setPositiveButton("확인") { dialog, _ ->
            quizPassword = inputView.findViewById<EditText>(R.id.d_password_et).text.toString()
            if(quizPassword.length != 4){
                Toast.makeText(this@AddQuizActivity, "비밀번호를 다시 입력해 주세요.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                return@setPositiveButton
            }

            val quizData = getQuizData()
            lifecycleScope.launch {

                val addQuizResponse = api.addQuiz(quizData)
                if (addQuizResponse.isSuccessful) {
                    Toast.makeText(this@AddQuizActivity, "퀴즈 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun getQuizData(): QuizData {


        return QuizData(
            name = binding.addQuizChoice1Et.text.toString(),
            password = quizPassword,
            choice1 = binding.addQuizChoice1Et.text.toString(),
            choice2 = binding.addQuizChoice2Tv.text.toString(),
            categories = categories
        )
    }
}