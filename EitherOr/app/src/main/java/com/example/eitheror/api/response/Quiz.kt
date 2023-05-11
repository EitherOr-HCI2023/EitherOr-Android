package com.example.eitheror.api.response

import java.time.LocalDateTime


data class Quiz(
    val id: Int?,
    val creationTime: LocalDateTime?,
    val hits: Int?,
    val password: String?,
    val contents: String?,
    val choice1: String?,
    val choice1SelectionNum: Int?,
    val choice2: String?,
    val choice2SelectionNum: Int?,
    val chatGPTComment: String?,
    val categoryId: Int?
    )

