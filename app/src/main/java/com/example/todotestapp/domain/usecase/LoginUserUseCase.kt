package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val todoRepo: ToDoRepository) {
    //by default all classes are final so inorder to enable it to override it again we use open
    suspend fun loginUserByEmail(email: String): Response<LoginResponse>{
        return todoRepo.loginUser(email)
    }
//    suspend fun loginUserByEmail(email: String): Response<LoginResponse> {
//        return todoRepo.loginUser(email)
//    }
}