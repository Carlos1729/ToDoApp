package com.example.todotestapp.data.api

import com.example.todotestapp.data.db.*
import retrofit2.Response
import retrofit2.http.*


//Create the API interface

interface ToDoApi {

    @GET("tasks")
    suspend fun getTask(
        @Query("id") id:Int
    ): Response<List<ToDo>>

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
        @Query("author_id") id:Int,
    ): Response<ListToDoResponse>

    @GET("tasks")
    suspend fun listToDoCompleted(
        @Query("author_id") id:Int,
        @Query("status") status: String
    ): Response<ListToDoResponse>

    @PATCH("tasks/{id}")
    suspend fun updateToDo(
        @Path("id") id:Int?,
        @Body requestBody: UpdateToDoRequest
    ):Response<UpdateToDoResponse>

    @DELETE("tasks/{id}")
    suspend fun deleteToDo(
        @Path("id") id:Int?,
    ):Response<BaseResponse>

}