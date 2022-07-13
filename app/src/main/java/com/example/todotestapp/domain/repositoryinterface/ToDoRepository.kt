package com.example.todotestapp.domain.repositoryinterface

import com.example.todotestapp.data.db.ToDo
import retrofit2.Response

interface ToDoRepository {

    suspend fun getTask(id: Int) : Response<List<ToDo>>


}