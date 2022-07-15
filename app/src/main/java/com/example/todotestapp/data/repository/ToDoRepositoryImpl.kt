package com.example.todotestapp.data.repository

import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserModel
import com.example.todotestapp.data.db.ToDo
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

//    override suspend fun signUpUser(requestBody: SignUpUserModel): Response<BaseResponse<SignUpUserModel>> {
//        return RetrofitInstance.api.signUpUser(requestBody)
//    }

}