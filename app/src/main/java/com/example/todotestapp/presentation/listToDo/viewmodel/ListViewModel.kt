package com.example.todotestapp.presentation.listToDo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.*
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

    val myToDoAllList: StateLiveData<Response<ListToDoResponse>> = StateLiveData()
    val myToDoListByStatus: StateLiveData<Response<ListToDoResponse>> = StateLiveData()
    val deleteToDoItemLiveData: StateLiveData<Response<BaseResponse>> = StateLiveData()

    fun getAllTasks(id: Int) {
        viewModelScope.launch {
            myToDoAllList.postLoading()
            val response: Response<ListToDoResponse> = toDoFetch.listToDoByEmail(id)
            if(response.isSuccessful) {
                myToDoAllList.postSuccess(response)
            }
            else if(response.code() == 404) {
                myToDoAllList.postSuccess(response)
            }
        }
    }

    fun getTasksByStatus(id: Int, status: String) {
        viewModelScope.launch {
            myToDoListByStatus.postLoading()
            val response: Response<ListToDoResponse> =
                toDoStatusFetch.listToDoByIdStatus(id, status)
            if(response.isSuccessful)
            {
                myToDoListByStatus.postSuccess(response)
            }
            else if(response.code() == 404)
            {
                myToDoListByStatus.postSuccess(response)
            }
        }
    }

    fun deleteToDo(id: Int?) {
        viewModelScope.launch {
            deleteToDoItemLiveData.postLoading()
            val response = id?.let { toDoDelete.deleteTaskById(it) }
            if (response != null) {
                if(response.isSuccessful) {
                    deleteToDoItemLiveData.postSuccess(response)
                }
            }
        }
    }

}