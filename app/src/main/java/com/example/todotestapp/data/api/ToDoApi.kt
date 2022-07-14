package com.example.todotestapp.data.api

import android.provider.ContactsContract
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.ToDo
import retrofit2.Response
import retrofit2.http.*

interface ToDoApi {

    @GET("tasks")
    suspend fun getTask(
        @Query("id") id:Int
    ): Response<List<ToDo>>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email:String
    ): Response<LoginResponse>

}