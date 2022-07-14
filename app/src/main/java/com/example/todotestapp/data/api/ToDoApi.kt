package com.example.todotestapp.data.api

import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserModel
import com.example.todotestapp.data.db.ToDo
import retrofit2.Response
import retrofit2.http.*

interface ToDoApi {

    @GET("tasks")
    suspend fun getTask(
        @Query("id") id:Int
    ): Response<List<ToDo>>

    @GET("login")
    suspend fun loginUser(
        @Query("email") email:String
    ): Response<LoginResponse>

    @POST("authors")//api end point goes here
    suspend fun signUpUser(
        @Body requestBody: SignUpUserModel
    ): Response<SignUpUserModel>


//    @POST("authors")//api end point goes here
//    suspend fun signUpUser(
//        @Body requestBody: SignUpUserModel
//    ): Response<SignUpUserModel>
//





//
//    @FormUrlEncoded
//    @POST("login")
//    suspend fun loginUser(
//        @Field("email") email:String
//    ): Response<LoginResponse>



}