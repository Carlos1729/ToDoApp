package com.example.todotestapp.domain.usecase

import com.example.todotestapp.data.db.ToDo
import com.example.todotestapp.data.repository.ToDoRepository
import retrofit2.Response

class ToDoUseCase(private val todoRepo: ToDoRepository) {
      //by default all classes are final so inorder to enable it to override it again we use open
    suspend fun execute(id: Int): Response<List<ToDo>>{
        return todoRepo.getTask(id)
    }
}