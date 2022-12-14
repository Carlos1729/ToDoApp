package com.example.todotestapp.presentation.listToDo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.usecase.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class ListViewModel @Inject constructor(toDoFetchUseCase : ListToDoUseCase
                                        , toDoStatusFetchUseCase : ListToDoByStatusUseCase,
                                        toDoDeleteUseCase : DeleteToDoUseCase, listToDoPaginationUseCase: ListToDoPaginationUseCase,updateToDoInListUseCase: UpdateToDoInListUseCase ) : ViewModel() {

    private val toDoFetch = toDoFetchUseCase
    private val toDoStatusFetch = toDoStatusFetchUseCase
    private val toDoDelete = toDoDeleteUseCase
    private val listToDoPagination = listToDoPaginationUseCase
    private val updateToDoInList = updateToDoInListUseCase
     var status : String? = null

    val myToDoAllList: StateLiveData<Response<ListToDoResponse>> = StateLiveData()
    val myToDoListByStatus: StateLiveData<Response<ListToDoResponse>> = StateLiveData()
    var myUpdateToDoInListResponse : StateLiveData<Response<UpdateToDoResponse>> = StateLiveData()
    val deleteToDoItemLiveData: StateLiveData<Response<BaseResponse>> = StateLiveData()
    var myToDoAllPaginationList: StateLiveData<Response<ListToDoPaginationResponse>> = StateLiveData()

    private var totalPages : Int = 1

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

    fun getAllTasksPagination(role:String,id:Int,pageNo:Int,priority:String?,orderBy:String?,sort:String?)
    {
        checkForUpdateInTotalPageNumber()
        viewModelScope.launch {
            if (pageNo <= totalPages) {
                myToDoAllPaginationList.postLoading()
                val response: Response<ListToDoPaginationResponse> =
                    listToDoPagination.listToDoPaginationById(
                        role,
                        id,
                        pageNo,
                        status,
                        priority,
                        orderBy,
                        sort
                    )
                if (response.isSuccessful) {
                    totalPages = response.body()?.totalPage ?: 1
                    myToDoAllPaginationList.postSuccess(response)
                } else if (response.code() == 404) {
                    myToDoAllPaginationList.postSuccess(response)
                }
            }
        }
    }

    private fun checkForUpdateInTotalPageNumber() {
        if(totalPages == 0){
            totalPages =1
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

    fun updateToDoInList(id: Int,requestBody: UpdateToDoRequest)
    {
        viewModelScope.launch {
            myUpdateToDoInListResponse.postLoading()
            val response = updateToDoInList.updateToDoInListByRequest(id,requestBody)
            if(response.isSuccessful)
            {
                myUpdateToDoInListResponse.postSuccess(response)
            }
            if(response.code() == 400)
            {
                myUpdateToDoInListResponse.postSuccess(response)
            }
        }
    }


    private val _sortByStored: MutableLiveData<Int> = MutableLiveData()
    val sortByStored: LiveData<Int> = _sortByStored
    fun setSortByStoredFromFrag(value : Int = 0)
    {
        _sortByStored.postValue(value)
    }

    private val _statusStored : MutableLiveData<Int> = MutableLiveData()
    val statusStored: LiveData<Int> = _statusStored
    fun setStatusFromFrag(value : Int = 0)
    {
        _statusStored.postValue(value)
    }

    private val _priorityStored : MutableLiveData<Int> = MutableLiveData()
    val priorityStored: LiveData<Int> = _priorityStored
    fun setPriorityFromFrag(value : Int = 0)
    {
        _priorityStored.postValue(value)
    }

    fun clearLiveData() {
        myToDoAllPaginationList = StateLiveData()
        myUpdateToDoInListResponse = StateLiveData()

    }

}