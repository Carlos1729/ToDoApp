package com.example.todotestapp.data.network

import com.example.todotestapp.model.ToDo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ToDoApi {

    @GET("tasks")
    suspend fun getTask(
        @Query("id") id:Int
    ): Response<List<ToDo>>

    @POST("tasks")
    suspend fun pushTask(
        @Body post: ToDo
    ):Response<ToDo>

}