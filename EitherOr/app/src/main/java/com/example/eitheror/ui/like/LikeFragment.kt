package com.example.eitheror.ui.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.databinding.FragmentHomeBinding
import com.example.eitheror.databinding.FragmentLikeBinding
import com.example.eitheror.ui.base.BaseFragment

class LikeFragment : BaseFragment() {
    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!

    private var likeAdapter: LikeAdapter? = null
    private var likeQuizList = ArrayList<Quiz>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        likeAdapter = LikeAdapter(likeQuizList)
        binding.fLikeListRv.adapter = likeAdapter
        binding.fLikeListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        likeAdapter!!.setOnItemClickListener(object : LikeAdapter.OnItemClickListener{
            override fun onItemClick(quiz: Quiz) {
                // 게임 액티비티로 전환
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}