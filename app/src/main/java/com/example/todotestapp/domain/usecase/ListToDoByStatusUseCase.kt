package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.ListToDoResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class ListToDoByStatusUseCase @Inject constructor(private val todoRepo: ToDoRepository) {

    suspend fun listToDoByIdStatus(id:Int, email: String): Response<ListToDoResponse>
    {
        return todoRepo.listToDoCompleted(id,email)
    }

}