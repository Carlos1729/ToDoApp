package com.example.todotestapp.data.api

import android.webkit.WebSettings
import com.example.todotestapp.data.db.*
import retrofit2.Response
import retrofit2.http.*


//Create the API interface

interface ToDoApi {

    @GET("tasks")
    suspend fun getTask(
        @Query("id") id:Int
    ): Response<List<ToDo>>

    @GET("login")
    suspend fun loginUser(
        @Query("email") email:String
    ): Response<LoginResponse>

    @POST("verify")//api end point goes here
    suspend fun loginUserByOTP(
        @Body requestBody: LoginOTPRequest
    ): Response<LoginOTPResponse>

    @POST("verify")//api end point goes here
    suspend fun signUpUserByOTP(
        @Body requestBody: SignUpOTPRequest
    ): Response<SignUpOTPResponse>

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
    suspend fun listToDoPagination(
        @Query("role") role:String,
        @Query("author_id") id:Int,
        @Query("page") pageNo:Int?,
        @Query("status") status: String?,
        @Query("priority") priority: String?
    ): Response<ListToDoPaginationResponse>

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