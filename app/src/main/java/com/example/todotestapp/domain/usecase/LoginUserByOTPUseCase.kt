package com.example.todotestapp.domain.usecase


import com.example.todotestapp.data.db.LoginOTPRequest
import com.example.todotestapp.data.db.LoginOTPResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class LoginUserByOTPUseCase @Inject constructor(private val todoRepo: ToDoRepository){

    suspend fun LoginUserByOTPEmail(loginOTPDetails: LoginOTPRequest): Response<LoginOTPResponse>
    {
        return todoRepo.loginUserByOTP(loginOTPDetails)
    }
}