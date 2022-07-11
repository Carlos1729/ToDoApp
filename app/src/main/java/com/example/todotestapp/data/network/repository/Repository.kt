package com.example.todotestapp.data.network.repository

import com.example.todotestapp.data.network.RetrofitInstance
import com.example.todotestapp.model.ToDo
import retrofit2.Response

class Repository {

    suspend fun getTask(id: Int) : Response<List<ToDo>>{
        return RetrofitInstance.api.getTask(id)
    }

}