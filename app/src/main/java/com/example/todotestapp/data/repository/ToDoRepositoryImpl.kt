package com.example.todotestapp.data.repository

import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response

class ToDoRepositoryImpl : ToDoRepository {


    //this an api call which it knows that it will take time to get the response so what is does it tries is that the thread that it is getting executing will now use its resourses to
    //execute other functions until we gwt a response this property of using the same thread is coroutines

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
        return RetrofitInstance.api.updateToDo(id,requestBody)
    }

    override suspend fun deleteToDo(id: Int?): Response<BaseResponse> {
        return RetrofitInstance.api.deleteToDo(id)
    }

}