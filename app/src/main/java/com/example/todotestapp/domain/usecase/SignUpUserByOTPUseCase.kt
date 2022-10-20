package com.example.todotestapp.domain.usecase


import com.example.todotestapp.data.db.SignUpOTPRequest
import com.example.todotestapp.data.db.SignUpOTPResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class SignUpUserByOTPUseCase @Inject constructor(private val todoRepo: ToDoRepository) {
    suspend fun SignUpUserByOTPEmail(signUpOTPDetails: SignUpOTPRequest) : Response<SignUpOTPResponse>
    {
        return todoRepo.signUpUserByOTP(signUpOTPDetails)
    }
}