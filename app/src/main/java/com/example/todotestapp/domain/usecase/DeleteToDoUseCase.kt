package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteToDoUseCase @Inject constructor(private val todoRepo: ToDoRepository) {
    suspend fun deleteTaskById(id: Int): Response<BaseResponse> {
        return todoRepo.deleteToDo(id)
    }
}