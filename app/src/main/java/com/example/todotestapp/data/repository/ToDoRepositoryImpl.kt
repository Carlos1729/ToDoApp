package com.example.todotestapp.data.repository

import com.example.todotestapp.data.db.ToDo
import retrofit2.Response

class ToDoRepositoryImpl : ToDoRepository {

    override
    suspend fun getTask(id: Int) : Response<List<ToDo>> {
        return RetrofitInstance.api.getTask(id)
    }

}