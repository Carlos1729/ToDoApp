package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.ListToDoPaginationResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import javax.inject.Inject

class ListToDoPaginationUseCase @Inject constructor(private val todoRepo: ToDoRepository)  {


    suspend fun listToDoPaginationById(role:String,id:Int,pageNo:Int?,status: String?,priority:String?) : Response<ListToDoPaginationResponse>
    {
        return todoRepo.listToDoPagination(role,id, pageNo,status,priority)
    }

}