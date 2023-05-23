package com.example.eitheror.api.response

import com.google.gson.annotations.SerializedName

data class Quiz(
    val name: String?,
    val id: Int?,
    val hits: Int?,
    val choice1: String?,

    @SerializedName("choice1SelectionNum")
    val choice1SelectionNum: Int?,
    val choice2: String?,

    @SerializedName("choice2SelectionNum")
    val choice2SelectionNum: Int?,

    @SerializedName("chatGPTComment")
    val chatGPTComment: String?,

    val categoryName: ArrayList<String>?
)

