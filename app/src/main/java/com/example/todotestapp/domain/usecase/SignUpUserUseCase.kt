package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.SignUpUserRequest
import com.example.todotestapp.data.db.SignUpUserResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response

class SignUpUserUseCase(private val todoRepo: ToDoRepository) {

    suspend fun SignUpUserByDetails(userEN: SignUpUserRequest): Response<SignUpUserResponse> {
        return todoRepo.signUpUser(userEN)
    }
}