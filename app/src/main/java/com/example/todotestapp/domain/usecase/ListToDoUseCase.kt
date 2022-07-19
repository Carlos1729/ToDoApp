package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.ListToDoResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class ListToDoUseCase @Inject constructor(private val todoRepo: ToDoRepository) {

    suspend fun listToDoByEmail(id:Int): Response<ListToDoResponse>
    {
        return todoRepo.listToDo(id)
    }

}