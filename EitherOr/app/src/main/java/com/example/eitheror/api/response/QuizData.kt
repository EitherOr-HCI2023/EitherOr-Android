package com.example.eitheror.api.response

import com.google.gson.annotations.SerializedName

data class QuizData(
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("choice1") val choice1: String,
    @SerializedName("choice2") val choice2: String,
    @SerializedName("categories") val categories: ArrayList<String>
)
