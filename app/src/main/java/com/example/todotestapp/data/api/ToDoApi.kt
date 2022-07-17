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

    @POST("tasks")//api end point goes here
    suspend fun addToDo(
        @Body requestBody: AddToDoRequest
    ): Response<AddToDoResponse>

    @GET("tasks")
    suspend fun listToDo(
        @Query("email") email:String
    ): Response<ListToDoResponse>

//    @GET("tasks")
//    suspend fun listToDoCompleted(
//        @Query("authorId") id:Int,
//        @Query("status") status: String
//    ): Response<ListToDoResponse>

    @PATCH("tasks/{id}")
    suspend fun updateToDo(
        @Path("id") id:Int?,
        @Body requestBody: UpdateToDoRequest
    ):Response<UpdateToDoResponse>

    @DELETE("tasks/{id}")
    suspend fun deleteToDo(
        @Path("id") id:Int?,
    ):Response<BaseResponse>

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