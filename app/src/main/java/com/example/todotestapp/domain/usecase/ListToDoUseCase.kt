package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.ListToDoResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response

class ListToDoUseCase(private val todoRepo: ToDoRepository) {

    suspend fun listToDoByEmail(email: String): Response<ListToDoResponse>
    {
        return todoRepo.listToDo(email)
    }

}