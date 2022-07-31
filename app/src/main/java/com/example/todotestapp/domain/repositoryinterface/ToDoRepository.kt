package com.example.todotestapp.domain.repositoryinterface

import com.example.todotestapp.data.db.*
import retrofit2.Response

interface ToDoRepository {

    suspend fun loginUser(email: String) : Response<LoginResponse>
    suspend fun loginUserByOTP(requestBody: LoginOTPRequest): Response<LoginOTPResponse>
    suspend fun signUpUser(requestBody: SignUpUserRequest): Response<SignUpUserResponse>
    suspend fun signUpUserByOTP(requestBody: SignUpOTPRequest): Response<SignUpOTPResponse>
    suspend fun addToDo(requestBody: AddToDoRequest): Response<AddToDoResponse>
    suspend fun listToDo(id: Int): Response<ListToDoResponse>
    suspend fun listToDoPagination(role: String,id: Int,pageNo: Int?,status: String?, priority:String?,orderBy:String?,sort:String?): Response<ListToDoPaginationResponse>
    suspend fun updateToDo(id: Int?,requestBody: UpdateToDoRequest): Response<UpdateToDoResponse>
    suspend fun updateToDoInList(id: Int?,requestBody: UpdateToDoRequest): Response<UpdateToDoResponse>
    suspend fun deleteToDo(id: Int?): Response<BaseResponse>
    suspend fun listToDoCompleted(id:Int,status: String) : Response<ListToDoResponse>

}