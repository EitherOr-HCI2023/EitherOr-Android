package com.example.eitheror.ui.like

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.databinding.ItemQuizBinding

class LikeAdapter(private val quizList: ArrayList<Quiz>) :
    RecyclerView.Adapter<LikeAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(quiz: Quiz)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }


    inner class ViewHolder(val binding: ItemQuizBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.itemQuizTitle.text = quiz.name
            binding.itemQuizHeartTv.text = quiz.hits.toString()

            binding.itemQuizCl.setOnClickListener {
                itemClickListener.onItemClick(quiz)
            }

            binding.itemQuizHeartCl.setOnClickListener{
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = quizList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quizList[position])
    }
}