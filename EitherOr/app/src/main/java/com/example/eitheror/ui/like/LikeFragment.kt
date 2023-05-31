package com.example.eitheror.ui.like

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.eitheror.App
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.api.response.QuizPage
import com.example.eitheror.databinding.FragmentHomeBinding
import com.example.eitheror.databinding.FragmentLikeBinding
import com.example.eitheror.db.UserDB
import com.example.eitheror.db.UserLike
import com.example.eitheror.ui.base.BaseFragment
import com.example.eitheror.ui.quiz.QuizActivity
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class LikeFragment : BaseFragment() {
    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!
    lateinit var userDB: UserDB
    private var likeAdapter: LikeAdapter? = null
    private var likeQuizList = ArrayList<UserLike>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userDB = UserDB.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        likeAdapter = LikeAdapter(likeQuizList)
        initQuizList()


        binding.fLikeListRv.adapter = likeAdapter
        binding.fLikeListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        likeAdapter!!.setOnItemClickListener(object : LikeAdapter.OnItemClickListener{
            override fun onItemClick(userLike: UserLike) {
                startActivity(Intent(requireContext(), QuizActivity::class.java).apply {
                    putExtra("quizType", "SELECTED")
                    putExtra("id", userLike.quizId)
                })
            }

            override fun onHeartClick(userLike: UserLike) {
                userDB.UserDao().deleteLike(1, userLike.quizId)
                App.spf.setBoolean(userLike.quizId.toString(), false)
                viewLifecycleOwner.lifecycleScope.launch {
                    val minusHitResponse = api.minusHit(userLike.quizId)
                    if (minusHitResponse.isSuccessful){
                        Log.i("hit", "${userLike.quizId} unhit success")
                    }
                }
            }

        })

    }

    fun initQuizList(){
        var quizlist = userDB.UserDao().getLikeQuizList(1)
        likeQuizList.addAll(quizlist)
        Log.i("LikeQL", likeQuizList.toString())
        likeAdapter!!.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}