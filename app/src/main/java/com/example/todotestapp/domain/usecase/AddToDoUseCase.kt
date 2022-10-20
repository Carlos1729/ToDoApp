package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.AddToDoRequest
import com.example.todotestapp.data.db.AddToDoResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class AddToDoUseCase @Inject constructor(private val todoRepo: ToDoRepository) {


    suspend fun addToDoByRequest(addToDoDetails: AddToDoRequest): Response<AddToDoResponse>
    {
        return todoRepo.addToDo(addToDoDetails)//remember add to itself is a suspended function
    }
}