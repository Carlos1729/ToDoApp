package com.example.todotestapp.data.api

import com.example.todotestapp.data.db.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ToDoApi {

    @GET("tasks")
    suspend fun getTask(
        @Query("id") id:Int
    ): Response<List<ToDo>>

//    @GET("author")
//    suspend fun loginUser(
//        @Query("email") email:String
//    ): Response<BaseResponse<LoginResponse>>

    @GET("author")
    suspend fun loginUser(
        @Query("email") email:String
    ): Response<LoginResponse>


    @POST("authors")//api end point goes here
    suspend fun signUpUser(
        @Body requestBody: SignUpUserRequest
    ): Response<SignUpUserResponse>


//    @POST("authors")//api end point goes here
//    suspend fun signUpUser(
//        @Body requestBody: SignUpUserResponse
//    ): Response<SignUpUserResponse>
//





//
//    @FormUrlEncoded
//    @POST("login")
//    suspend fun loginUser(
//        @Field("email") email:String
//    ): Response<LoginResponse>



}