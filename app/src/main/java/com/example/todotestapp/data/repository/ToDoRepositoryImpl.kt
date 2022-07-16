package com.example.todotestapp.data.repository

import android.util.Log
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

    override suspend fun listToDo(email: String): Response<ListToDoResponse> {
        return RetrofitInstance.api.listToDo(email)
    }

    override suspend fun updateToDo(id: Int?, requestBody: UpdateToDoRequest): Response<UpdateToDoResponse>
    {
//        Log.v("MadhukarKolli",id.toString())
        return RetrofitInstance.api.updateToDo(id,requestBody)
    }

    override suspend fun deleteToDo(id: Int?): Response<BaseResponse> {
        return RetrofitInstance.api.deleteToDo(id)
    }

}