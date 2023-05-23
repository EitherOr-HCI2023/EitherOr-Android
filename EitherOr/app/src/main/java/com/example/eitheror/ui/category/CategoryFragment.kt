package com.example.eitheror.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eitheror.R
import com.example.eitheror.databinding.FragmentCategoryBinding
import com.example.eitheror.ui.quizlist.QuizListFragment

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    fun initClickListener() {

        binding.fCategoryFun.setOnClickListener {
            sendTagToFragment("재미")
        }

        binding.fCategoryDaily.setOnClickListener {
            sendTagToFragment("일상")
        }

        binding.fCategoryLove.setOnClickListener {
            sendTagToFragment("연인")

        }
        binding.fCategoryFood.setOnClickListener {
            sendTagToFragment("음식")

        }
        binding.fCategoryTaste.setOnClickListener {
            sendTagToFragment("취향")

        }
        binding.fCategoryIf.setOnClickListener {
            sendTagToFragment("만약")

        }
        binding.fCategoryJob.setOnClickListener {
            sendTagToFragment("직장")

        }
        binding.fCategoryCaution.setOnClickListener {
            sendTagToFragment("비위조심")

        }
        binding.fCategoryLife.setOnClickListener {
            sendTagToFragment("인생")
         }

    }

    fun sendTagToFragment(tag: String){

        val quizListFragment = QuizListFragment()
        val bundle = Bundle()
        bundle.putString("tag", tag)
        quizListFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frm, quizListFragment)
            .addToBackStack(null)
            .commit()
    }


}