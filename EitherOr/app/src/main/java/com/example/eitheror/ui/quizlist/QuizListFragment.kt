package com.example.eitheror.ui.quizlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eitheror.App
import com.example.eitheror.R
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.api.response.QuizPage
import com.example.eitheror.databinding.FragmentQuizListBinding
import com.example.eitheror.ui.base.BaseFragment
import com.example.eitheror.ui.category.CategoryFragment
import com.example.eitheror.ui.home.HomeFragment
import com.example.eitheror.ui.quiz.QuizActivity
import kotlinx.coroutines.launch

class QuizListFragment : BaseFragment() {

    private var _binding: FragmentQuizListBinding? = null
    private val binding get() = _binding!!
    var adapter: QuizListAdapter? = null

    var categoryName: String? = null
    var pageCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            categoryName = it.getString("tag")
        }

        if (categoryName == null) {
            Toast.makeText(requireContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commit()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {

            if (categoryName == "recent") {
                // 최신
                val recentQuizListResponse = api.getRecentQuizList()
                if (recentQuizListResponse.isSuccessful) {
                    adapter = QuizListAdapter(recentQuizListResponse.body()!!)
                    setAdapter()
                }
            } else if (categoryName == "popular") {
                // 인기
                val popularQuizPageResponse = api.getPopularList()
                if (popularQuizPageResponse.isSuccessful) {
                    adapter = QuizListAdapter(popularQuizPageResponse.body()!!)
                    setAdapter()
                }
            } else {
                val quizPageResponse = api.getQuizList(categoryName!!, pageCount)
                if (quizPageResponse.isSuccessful) {
                    adapter = QuizListAdapter(quizPageResponse.body()!!)
                    setAdapter()
                }

            }
        }


        binding.fQlBackIv.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, CategoryFragment())
                .commit()
        }


    }


    fun setAdapter() {
        binding.fQuizListRv.adapter = adapter
        binding.fQuizListRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.fQuizListRv.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        adapter!!.setOnItemClickListener(object : QuizListAdapter.OnItemClickListener {
            override fun onItemClick(quizPage: QuizPage) {
                val intent = Intent(requireContext(), QuizActivity::class.java)
                intent.putExtra("quizType", "SELECTED")
                intent.putExtra("id", quizPage.id)
                startActivity(intent)
            }

            override fun onHeartClick(quizPage: QuizPage) {
                val isLike = App.spf.getBoolean(quizPage.id.toString(), false)

                if (!isLike){
                    viewLifecycleOwner.lifecycleScope.launch {
                        val plusHitResponse = api.plusHit(quizPage.id!!)
                        if (plusHitResponse.isSuccessful){
                            Log.i("hit", "${quizPage.id} hit success")
                        }
                    }
                }else{
                    viewLifecycleOwner.lifecycleScope.launch {
                        val minusHitResponse = api.minusHit(quizPage.id!!)
                        if (minusHitResponse.isSuccessful){
                            Log.i("hit", "${quizPage.id} unhit success")
                        }
                    }
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}