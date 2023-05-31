package com.example.eitheror.db

import androidx.room.*


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLike(userLike: UserLike)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM UserLikeTable WHERE userId = :userId")
    fun getLikeQuizList(userId: Int): List<UserLike>

    @Query("DELETE FROM UserLikeTable WHERE userId = :userId AND quizId = :quizId")
    fun deleteLike(userId: Int, quizId: Int)

}