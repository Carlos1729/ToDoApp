package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class AddAdminUseCase @Inject constructor(private val todoRepo: ToDoRepository) {

    suspend fun addAdminByRequest(id:Int?,addAdminDetails: AddAdminRequest):Response<AddAdminResponse>
    {
        return todoRepo.grantPermission(id,addAdminDetails)
    }
}