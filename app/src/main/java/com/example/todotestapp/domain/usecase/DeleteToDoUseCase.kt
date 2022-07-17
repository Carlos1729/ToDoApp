package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response

class DeleteToDoUseCase(private val todoRepo: ToDoRepository) {
    suspend fun deleteTaskById(id: Int): Response<BaseResponse> {
        return todoRepo.deleteToDo(id)
    }
}