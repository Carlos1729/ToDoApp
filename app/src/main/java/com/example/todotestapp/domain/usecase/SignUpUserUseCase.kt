package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserModel
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response

class SignUpUserUseCase (private val todoRepo: ToDoRepository){

//    suspend fun signUpUserByDetails(requestBody: SignUpUserModel): Response<BaseResponse<SignUpUserModel>> {
//        return todoRepo.signUpUser(requestBody)
//    }

}