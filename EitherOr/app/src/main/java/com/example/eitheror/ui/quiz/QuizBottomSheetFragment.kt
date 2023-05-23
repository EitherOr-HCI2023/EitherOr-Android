package com.example.eitheror.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eitheror.databinding.QuizChatgptBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QuizBottomSheetFragment(val chatGPTComment: String?): BottomSheetDialogFragment() {
    lateinit var binding: QuizChatgptBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuizChatgptBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (chatGPTComment != null){
            binding.bottomSheetTv.text = chatGPTComment
        }else{
            binding.bottomSheetTv.text = "ChatGPT 응답이 없습니다."
        }

        binding.bottomSheetNextBtn.setOnClickListener {
            activity?.finish()
            dismiss()
        }

    }

}