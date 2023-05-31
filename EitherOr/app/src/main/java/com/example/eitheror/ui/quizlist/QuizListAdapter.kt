package com.example.eitheror.ui.quizlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eitheror.App
import com.example.eitheror.api.response.QuizPage
import com.example.eitheror.databinding.ItemLoadingBinding
import com.example.eitheror.databinding.ItemQuizBinding

class QuizListAdapter(private val quizList: ArrayList<QuizPage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_QUIZ = 0
        private const val TYPE_LOADING = 1
    }

    private lateinit var itemClickListener: OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(quizPage: QuizPage)
        fun onHeartClick(quizPage: QuizPage)
    }


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }


    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    inner class QuizViewHolder(val binding: ItemQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quizPage: QuizPage) {
            binding.itemQuizTitle.text = quizPage.name
            binding.itemQuizHeartTv.text = quizPage.hits.toString()

            var isLike = App.spf.getBoolean(quizPage.id.toString(), false)

            if (!isLike) {
                binding.itemQuizHeartOffIv.visibility = View.VISIBLE
                binding.itemQuizHeartOnIv.visibility = View.GONE
            } else {
                binding.itemQuizHeartOffIv.visibility = View.GONE
                binding.itemQuizHeartOnIv.visibility = View.VISIBLE
            }

            binding.itemQuizCl.setOnClickListener {
                itemClickListener.onItemClick(quizPage)
            }

            binding.itemQuizHeartCl.setOnClickListener {
                itemClickListener.onHeartClick(quizPage)
                isLike = App.spf.getBoolean(quizPage.id.toString(), false)

                if (!isLike) { // false -> 클릭 -> true
                    binding.itemQuizHeartOffIv.visibility = View.GONE
                    binding.itemQuizHeartOnIv.visibility = View.VISIBLE
                    App.spf.setBoolean(quizPage.id.toString(), true)
                    binding.itemQuizHeartTv.text =
                        (binding.itemQuizHeartTv.text.toString().toInt() + 1).toString()




                } else {
                    binding.itemQuizHeartOffIv.visibility = View.VISIBLE
                    binding.itemQuizHeartOnIv.visibility = View.GONE
                    App.spf.setBoolean(quizPage.id.toString(), false)
                    binding.itemQuizHeartTv.text =
                        (binding.itemQuizHeartTv.text.toString().toInt() - 1).toString()
                }
            }
        }
    }

    fun addQuizList(ql: List<QuizPage>) {
        quizList.addAll(ql)
        quizList.add(QuizPage(null, null, null, null))
    }

    fun deleteLoadingView() {
        quizList.removeAt(quizList.lastIndex)
    }

    override fun getItemViewType(position: Int): Int {
        return when (quizList[position].id) {
            null -> TYPE_LOADING
            else -> TYPE_QUIZ
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_QUIZ -> {
                val binding =
                    ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return QuizViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return LoadingViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int = quizList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QuizViewHolder) {
            holder.bind(quizList[position])
        }
    }
}