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
import javax.inject.Inject

class ListViewModel @Inject constructor(toDoFetchUseCase : ListToDoUseCase
                                        , toDoStatusFetchUseCase : ListToDoByStatusUseCase,
                                        toDoDeleteUseCase : DeleteToDoUseCase ) : ViewModel() {

    private val toDoFetch = toDoFetchUseCase
    private val toDoStatusFetch = toDoStatusFetchUseCase
    private val toDoDelete = toDoDeleteUseCase

    val myToDoList: MutableLiveData<Response<ListToDoResponse>> = MutableLiveData()
    val deleteToDoItemLiveData: MutableLiveData<Response<BaseResponse>> = MutableLiveData()

    fun getTasks(id: Int) {
        viewModelScope.launch {
            val response: Response<ListToDoResponse> = toDoFetch.listToDoByEmail(id)
            myToDoList.value = response
        }
    }

    fun getTasksByStatus(id: Int, status: String) {
        viewModelScope.launch {
            val response: Response<ListToDoResponse> =
                toDoStatusFetch.listToDoByIdStatus(id, status)
            myToDoList.value = response
        }
    }

    fun deleteToDo(id: Int?) {
        viewModelScope.launch {
            val response = id?.let { toDoDelete.deleteTaskById(it) }
            deleteToDoItemLiveData.value = response
        }
    }

}