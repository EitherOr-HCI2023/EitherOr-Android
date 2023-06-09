package com.example.eitheror.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eitheror.R
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.databinding.FragmentHomeBinding
import com.example.eitheror.ui.category.CategoryFragment
import com.example.eitheror.ui.quiz.QuizActivity
import com.example.eitheror.ui.quizlist.QuizListFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClickListener(){
        binding.fHomeCategoryCv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, CategoryFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.fHomeRecentCv.setOnClickListener{
            val quizListFragment = QuizListFragment()
            val quizBundle = Bundle()
            quizBundle.putString("tag", "recent")
            quizListFragment.arguments = quizBundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, quizListFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.fHomeRandomBtn.setOnClickListener {
            val intent = Intent(requireContext(), QuizActivity::class.java)
            intent.putExtra("quizType", "RANDOM")
            startActivity(intent)
        }

        binding.fHomePopularCv.setOnClickListener {
            val quizListFragment = QuizListFragment()
            val quizBundle = Bundle()
            quizBundle.putString("tag", "popular")
            quizListFragment.arguments = quizBundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, quizListFragment)
                .addToBackStack(null)
                .commit()
        }

    }
}
