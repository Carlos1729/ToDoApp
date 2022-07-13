package com.example.todotestapp.presentation.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.ToDo
import com.example.todotestapp.data.repository.ToDoRepository
import com.example.todotestapp.domain.usecase.ToDoUseCase
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: ToDoRepository) : ViewModel(){

    private val fetchUserList = ToDoUseCase(repository)
//    by lazy{
//        ToDoUseCase(repository)
//    }
        val mytasks : MutableLiveData<Response<List<ToDo>>> = MutableLiveData()

        fun getTask(id: Int){
            viewModelScope.launch {
                val response : Response<List<ToDo>> = fetchUserList.execute(id)
                mytasks.value = response
            }
        }
}