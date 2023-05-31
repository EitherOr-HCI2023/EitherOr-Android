package com.example.eitheror.ui.quizlist

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.eitheror.db.UserDB
import com.example.eitheror.db.UserLike
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
    var isDataFinished = false
    lateinit var userDB:UserDB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userDB = UserDB.getInstance(requireContext())!!
    }
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
                val recentQuizListResponse = api.getRecentQuizList(pageCount)
                if (recentQuizListResponse.isSuccessful) {
                    adapter = QuizListAdapter(recentQuizListResponse.body()!!)
                    setAdapter()
                }
            } else if (categoryName == "popular") {
                // 인기
                val popularQuizPageResponse = api.getPopularList(pageCount)
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
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
                .commit()
        }


        binding.fQuizListRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastItemPos =
                    (recyclerView.layoutManager as LinearLayoutManager)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                if (!isDataFinished) {
                    if (!binding.fQuizListRv.canScrollVertically(1) && lastItemPos == itemTotalCount) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            pageCount += 1
                            if (categoryName == "recent") {
                                // 최신
                                val recentQuizListResponse = api.getRecentQuizList(pageCount)
                                if (recentQuizListResponse.isSuccessful) {
                                    if (recentQuizListResponse.body()!!.isEmpty()) {
                                        isDataFinished = true
                                        adapter!!.deleteLoadingView()
                                    } else {
                                        adapter!!.addQuizList(recentQuizListResponse.body()!!)
                                    }

                                }
                            } else if (categoryName == "popular") {
                                // 인기
                                val popularQuizPageResponse = api.getPopularList(pageCount)
                                if (popularQuizPageResponse.isSuccessful) {
                                    if (popularQuizPageResponse.body()!!.isEmpty()) {
                                        isDataFinished = true
                                        adapter!!.deleteLoadingView()

                                    } else {
                                        adapter!!.addQuizList(popularQuizPageResponse.body()!!)

                                    }
                                }
                            } else {
                                val quizPageResponse = api.getQuizList(categoryName!!, pageCount)
                                if (quizPageResponse.isSuccessful) {
                                    if (quizPageResponse.body()!!.isEmpty()) {
                                        isDataFinished = true
                                        if (itemTotalCount > 10) {
                                            adapter!!.deleteLoadingView()
                                        }
                                    } else {
                                        adapter!!.addQuizList(quizPageResponse.body()!!)

                                    }
                                }
                            }
                            adapter!!.notifyDataSetChanged()

                        }


                    }
                }

            }
        })


    }

    fun setAdapter() {
        binding.fQuizListRv.adapter = adapter
        binding.fQuizListRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.fQuizListRv.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        adapter!!.setOnItemClickListener(object : QuizListAdapter.OnItemClickListener {
            override fun onItemClick(quizPage: QuizPage) {
                val intent = Intent(requireContext(), QuizActivity::class.java)
                intent.putExtra("quizType", "SELECTED")
                intent.putExtra("id", quizPage.id)
                startActivity(intent)
            }

            override fun onHeartClick(quizPage: QuizPage) {
                val isLike = App.spf.getBoolean(quizPage.id.toString(), false)

                if (!isLike) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val plusHitResponse = api.plusHit(quizPage.id!!)
                        if (plusHitResponse.isSuccessful) {
                            Log.i("hit", "${quizPage.id} hit success")

                            userDB.UserDao().insertLike(
                                UserLike(
                                    userId = 1,
                                    quizId = quizPage.id!!,
                                    hits = quizPage.hits!!,
                                    name = quizPage.name!!
                                )
                            )
                        }

                    }

                } else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val minusHitResponse = api.minusHit(quizPage.id!!)
                        if (minusHitResponse.isSuccessful) {
                            Log.i("hit", "${quizPage.id} unhit success")
                            userDB.UserDao().deleteLike(1, quizPage.id!!)

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