package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.UpdateToDoRequest
import com.example.todotestapp.data.db.UpdateToDoResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response

class UpdateToDoUseCase(private val todoRepo: ToDoRepository) {

    suspend fun updateToDoByRequest(id:Int?,updateToDoDetails: UpdateToDoRequest): Response<UpdateToDoResponse>
    {
        return todoRepo.updateToDo(id,updateToDoDetails)
    }

}