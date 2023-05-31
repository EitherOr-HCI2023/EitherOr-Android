package com.example.eitheror.api

import android.content.Context
import com.example.eitheror.api.response.Quiz
import com.example.eitheror.api.response.QuizData
import com.example.eitheror.api.response.QuizPage
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    companion object {
        private var INSTANCE: ApiService? = null

        private fun create(context: Context): ApiService{
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://112.187.189.12:8081")
                .build()
                .create(ApiService::class.java)
        }

        fun init(context: Context) = INSTANCE ?: synchronized(this){
            INSTANCE ?: create(context).also {
                INSTANCE = it
            }
        }

        fun getInstance(): ApiService = INSTANCE!!

    }

    @GET("/article/category/{categoryName}/{pageNum}")
    suspend fun getQuizList(
        @Path("categoryName") categoryName: String,
        @Path("pageNum") pageNum: Int = 1
    ): Response<ArrayList<QuizPage>>

    @GET("/article/{articleId}")
    suspend fun getQuiz(
        @Path("articleId") articleId: Int
    ): Response<Quiz>

    @GET("/article/popular/{pageNum}")
    suspend fun getPopularList(
        @Path("pageNum") pageNum: Int = 1
    ): Response<ArrayList<QuizPage>>

    @GET("/article/arbitrary/{categoryName}")
    suspend fun getRandomQuiz(
        @Path("categoryName") categoryName: String = "all"
    ): Response<Quiz>

    @GET("/article/list/{page}")
    suspend fun getRecentQuizList(
        @Path("page") page: Int = 1
    ): Response<ArrayList<QuizPage>>

    @PUT("/article/response/{articleId}/{choiceNumber}")
    suspend fun registerUserChoice(
        @Path("articleId") articleId: Int,
        @Path("choiceNumber") choiceNumber: Int
    ): Response<String>

    @PUT("/article/hit/{articleId}")
    suspend fun plusHit(
        @Path("articleId") articleId: Int
    ):Response<Unit>

    @PUT("/article/unhit/{articleId}")
    suspend fun minusHit(
        @Path("articleId") articleId: Int
    ):Response<Unit>

    @GET("/article/category")
    suspend fun getCategory()
    : Response<ArrayList<String>>

    @POST("/article/add")
    suspend fun addQuiz(
        @Body quizData: QuizData
    ): Response<String>

}