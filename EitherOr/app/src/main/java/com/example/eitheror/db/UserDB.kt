package com.example.eitheror.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, UserLike::class], version = 1)
abstract class UserDB : RoomDatabase() {
    abstract fun UserDao(): UserDao

    companion object {
        private var instance: UserDB? = null

        @Synchronized
        fun getInstance(context: Context): UserDB? {
            if (instance == null) {
                synchronized(UserDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDB::class.java,
                        "user-database"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}