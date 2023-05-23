package com.example.eitheror.ui.quizlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eitheror.App
import com.example.eitheror.api.response.QuizPage
import com.example.eitheror.databinding.ItemQuizBinding

class QuizListAdapter(private val quizList: ArrayList<QuizPage>) :
    RecyclerView.Adapter<QuizListAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(quizPage: QuizPage)
        fun onHeartClick(quizPage:QuizPage)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }


    inner class ViewHolder(val binding: ItemQuizBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quizPage: QuizPage) {
            binding.itemQuizTitle.text = quizPage.name
            binding.itemQuizHeartTv.text = quizPage.hits.toString()

            var isLike = App.spf.getBoolean(quizPage.id.toString(), false)

            if (!isLike){
                binding.itemQuizHeartOffIv.visibility = View.VISIBLE
                binding.itemQuizHeartOnIv.visibility = View.GONE
            }else{
                binding.itemQuizHeartOffIv.visibility = View.GONE
                binding.itemQuizHeartOnIv.visibility = View.VISIBLE
            }

            binding.itemQuizCl.setOnClickListener {
                itemClickListener.onItemClick(quizPage)
            }

            binding.itemQuizHeartCl.setOnClickListener {
                itemClickListener.onHeartClick(quizPage)
                isLike = App.spf.getBoolean(quizPage.id.toString(), false)

                if (!isLike){ // false -> 클릭 -> true
                    binding.itemQuizHeartOffIv.visibility = View.GONE
                    binding.itemQuizHeartOnIv.visibility = View.VISIBLE
                    App.spf.setBoolean(quizPage.id.toString(), true)
                    binding.itemQuizHeartTv.text = (binding.itemQuizHeartTv.text.toString().toInt() + 1).toString()
                }else{
                    binding.itemQuizHeartOffIv.visibility = View.VISIBLE
                    binding.itemQuizHeartOnIv.visibility = View.GONE
                    App.spf.setBoolean(quizPage.id.toString(), false)
                    binding.itemQuizHeartTv.text = (binding.itemQuizHeartTv.text.toString().toInt() - 1).toString()
                }
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