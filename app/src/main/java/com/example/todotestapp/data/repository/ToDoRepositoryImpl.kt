package com.example.todotestapp.data.repository

import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import okhttp3.RequestBody
import retrofit2.Response

class ToDoRepositoryImpl : ToDoRepository {

//    override suspend fun getTask(id: Int) : Response<List<ToDo>> {
//        return RetrofitInstance.api.getTask(id)
//    }

//    override suspend fun loginUser(email: String): Response<LoginResponse> {
//        return RetrofitInstance.api.loginUser(email)
//    }

    override suspend fun loginUser(email: String): Response<LoginResponse> {
        return RetrofitInstance.api.loginUser(email)
    }



    override suspend fun signUpUser(requestBody: SignUpUserRequest): Response<SignUpUserResponse> {
        return RetrofitInstance.api.signUpUser(requestBody)
    }

    override suspend fun addToDo(requestBody: AddToDoRequest): Response<AddToDoResponse> {
        return RetrofitInstance.api.addToDo(requestBody)
    }

}