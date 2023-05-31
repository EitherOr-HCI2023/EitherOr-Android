package com.example.eitheror.ui.like

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eitheror.App
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.api.response.QuizPage
import com.example.eitheror.databinding.ItemLikeBinding
import com.example.eitheror.databinding.ItemQuizBinding
import com.example.eitheror.db.UserLike

class LikeAdapter(private val likeList: ArrayList<UserLike>) :
    RecyclerView.Adapter<LikeAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(userLike: UserLike)
        fun onHeartClick(userLike: UserLike)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }


    inner class ViewHolder(val binding: ItemLikeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userLike: UserLike) {
            binding.itemLikeTitle.text = userLike.name
            binding.itemLikeHeartTv.text = (userLike.hits+1).toString()

            binding.itemLikeCl.setOnClickListener {
                itemClickListener.onItemClick(userLike)
            }

            binding.itemLikeHeartCl.setOnClickListener{
                itemClickListener.onHeartClick(userLike)
                binding.itemLike.visibility = View.GONE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLikeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = likeList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(likeList[position])
    }
}