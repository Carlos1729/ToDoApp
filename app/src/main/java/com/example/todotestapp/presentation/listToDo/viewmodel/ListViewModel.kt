package com.example.todotestapp.presentation.listToDo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.AddToDoUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import com.example.todotestapp.domain.usecase.ToDoUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: ToDoRepository) : ViewModel(){




//    private val fetch = ToDoUseCase(repository)
//
//        val mytasks : MutableLiveData<Response<List<ToDo>>> = MutableLiveData()
//
//        fun getTask(id: Int){
//            viewModelScope.launch {
//                val response : Response<List<ToDo>> = fetch.fetchTaskByUserID(id)
//                mytasks.value = response
//            }
//        }
}