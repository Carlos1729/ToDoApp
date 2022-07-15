package com.example.todotestapp.domain.repositoryinterface

import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserModel
import com.example.todotestapp.data.db.ToDo
import retrofit2.Response

interface ToDoRepository {

//    suspend fun getTask(id: Int) : Response<List<ToDo>>
//    suspend fun loginUser(email: String) : Response<LoginResponse>
    suspend fun loginUser(email: String) : Response<LoginResponse>
//    suspend fun signUpUser(requestBody: SignUpUserModel): Response<BaseResponse<SignUpUserModel>>
}