package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.UpdateToDoRequest
import com.example.todotestapp.data.db.UpdateToDoResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateToDoInListUseCase @Inject constructor(private val todoRepo: ToDoRepository) {

    suspend fun updateToDoInListByRequest(id:Int?,updateToDoDetails: UpdateToDoRequest): Response<UpdateToDoResponse>
    {
        return todoRepo.updateToDoInList(id,updateToDoDetails)
    }

}