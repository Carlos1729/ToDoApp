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

class ListViewModel(private val repository: ToDoRepository) : ViewModel() {


    private val toDoFetch = ListToDoUseCase(repository)
    private val toDoStatusFetch = ListToDoByStatusUseCase(repository)
    private val toDoDelete = DeleteToDoUseCase(repository)

    val myToDoList: StateLiveData<Response<ListToDoResponse>> = StateLiveData()
    val deleteToDoItemLiveData: StateLiveData<Response<BaseResponse>> = StateLiveData()

    fun getTasks(id: Int) {
        viewModelScope.launch {
            myToDoList.postLoading()
            val response: Response<ListToDoResponse> = toDoFetch.listToDoByEmail(id)
            if(response.isSuccessful)
            {
                myToDoList.postSuccess(response)
            }
            else if(response.code() == 404)
            {
                myToDoList.postSuccess(response)
            }
        }
    }

    fun getTasksByStatus(id: Int, status: String) {
        viewModelScope.launch {
            myToDoList.postLoading()
            val response: Response<ListToDoResponse> =
                toDoStatusFetch.listToDoByIdStatus(id, status)
            if(response.isSuccessful)
            {
                myToDoList.postSuccess(response)
            }
            else if(response.code() == 404)
            {
                myToDoList.postSuccess(response)
            }
        }
    }

    fun deleteToDo(id: Int?) {
        viewModelScope.launch {
            val response = id?.let { toDoDelete.deleteTaskById(it) }
            if (response != null) {
                if(response.isSuccessful) {
                    deleteToDoItemLiveData.postSuccess(response)
                }
            }
        }
    }

}