package com.example.todotestapp.presentation.listToDo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.DeleteToDoUseCase
import com.example.todotestapp.domain.usecase.ListToDoByStatusUseCase
import com.example.todotestapp.domain.usecase.ListToDoUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: ToDoRepository) : ViewModel(){


    private val todofetch = ListToDoUseCase(repository)

        val myToDoList : MutableLiveData<Response<ListToDoResponse>> = MutableLiveData()

        fun getTasks(id: Int){
            viewModelScope.launch {
                val response : Response<ListToDoResponse> = todofetch.listToDoByEmail(id)
                myToDoList.value = response
            }
        }

    private val todostatusfetch = ListToDoByStatusUseCase(repository)

    val myToDoListStatus : MutableLiveData<Response<ListToDoResponse>> = MutableLiveData()


    fun getTasksByStatus(id:Int,status : String){
        viewModelScope.launch {
            val response : Response<ListToDoResponse> = todostatusfetch.listToDoByIdStatus(id,status)
            myToDoListStatus.value = response
        }
    }

    private val tododelete = DeleteToDoUseCase(repository)

    val myDeleteToDoResponse : MutableLiveData<Response<BaseResponse>> = MutableLiveData()

    fun deleteToDo(id: Int?) {
        viewModelScope.launch {
            val response = id?.let { tododelete.deleteTaskById(it) }
            myDeleteToDoResponse.value = response
        }
    }

}