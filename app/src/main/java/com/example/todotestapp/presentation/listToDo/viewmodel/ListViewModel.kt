package com.example.todotestapp.presentation.listToDo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.AddToDoUseCase
import com.example.todotestapp.domain.usecase.ListToDoUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import com.example.todotestapp.domain.usecase.ToDoUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: ToDoRepository) : ViewModel(){


    private val todofetch = ListToDoUseCase(repository)

        val myToDoList : MutableLiveData<Response<ListToDoResponse>> = MutableLiveData()

        fun getTasks(email: String){
            viewModelScope.launch {
                val response : Response<ListToDoResponse> = todofetch.listToDoByEmail(email)
                myToDoList.value = response
            }
        }

}