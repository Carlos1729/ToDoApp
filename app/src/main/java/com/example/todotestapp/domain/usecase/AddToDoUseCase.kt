package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.AddToDoRequest
import com.example.todotestapp.data.db.AddToDoResponse
import com.example.todotestapp.data.db.SignUpUserRequest
import com.example.todotestapp.data.db.SignUpUserResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response

class AddToDoUseCase(private val todoRepo: ToDoRepository) {


    suspend fun addToDoByRequest(addToDoDetails: AddToDoRequest): Response<AddToDoResponse>
    {
        return todoRepo.addToDo(addToDoDetails)
    }
}