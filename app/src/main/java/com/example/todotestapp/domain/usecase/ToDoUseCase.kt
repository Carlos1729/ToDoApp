package com.example.todotestapp.domain.usecase

import com.example.todotestapp.domain.repositoryinterface.ToDoRepository

class ToDoUseCase(private val todoRepo: ToDoRepository) {
      //by default all classes are final so inorder to enable it to override it again we use open
//    suspend fun fetchTaskByUserID(id: Int): Response<List<ToDo>>{
//        return todoRepo.getTask(id)
//    }
}