package com.example.todotestapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.todotestapp.data.db.BaseListToDoResponse
import com.example.todotestapp.data.db.ListToDoPaginationResponse
import com.example.todotestapp.data.paging.ListToDoPagingSource
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import retrofit2.Response
import java.util.concurrent.Flow
import javax.inject.Inject

class ListToDoPaginationUseCase @Inject constructor(private val todoRepo: ToDoRepository)  {

    fun listToDoPaginationById(role:String,id:Int,pageNo:Int?,status: String?,priority:String?
                               ,orderBy:String?,sort: String?) : Flow<PagingData<Response<ListToDoPaginationResponse>>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
            }

        ).flow
    }

}