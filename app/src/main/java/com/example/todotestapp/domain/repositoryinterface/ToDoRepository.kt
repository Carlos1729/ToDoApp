package com.example.todotestapp.domain.repositoryinterface

import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserRequest
import com.example.todotestapp.data.db.SignUpUserResponse
import okhttp3.RequestBody
import retrofit2.Response

interface ToDoRepository {

//    suspend fun getTask(id: Int) : Response<List<ToDo>>
//    suspend fun loginUser(email: String) : Response<LoginResponse>
    suspend fun loginUser(email: String) : Response<LoginResponse>
    suspend fun signUpUser(requestBody: SignUpUserRequest): Response<SignUpUserResponse>
}