package com.example.eitheror.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey val id: Int,
    var userName: String
)

@Entity(tableName = "UserLikeTable")
data class UserLike(
    var userId: Int,
    var quizId: Int,
    var hits: Int,
    var name: String,
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}