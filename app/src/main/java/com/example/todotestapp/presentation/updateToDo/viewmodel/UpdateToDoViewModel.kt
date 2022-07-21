package com.example.todotestapp.presentation.updateToDo.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.R
import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.AddToDoUseCase
import com.example.todotestapp.domain.usecase.DeleteToDoUseCase
import com.example.todotestapp.domain.usecase.UpdateToDoUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class UpdateToDoViewModel(private val repository: ToDoRepository) : ViewModel(){

    private val todoadd = AddToDoUseCase(repository)

    val myAddToDoResponse : StateLiveData<Response<AddToDoResponse>> = StateLiveData()

//    val myAddToDoResponse : MutableLiveData<Response<AddToDoResponse>> = MutableLiveData()

    fun addToDo(requestBody: AddToDoRequest) {
        viewModelScope.launch {
            myAddToDoResponse.postLoading()
            val response = todoadd.addToDoByRequest(requestBody)
            if(response.isSuccessful)
            {
                myAddToDoResponse.postSuccess(response)
            }
        }
    }

    private val todoupdate = UpdateToDoUseCase(repository)

    val myUpdateToDoResponse : StateLiveData<Response<UpdateToDoResponse>> = StateLiveData()

    fun updateToDo(id: Int?, requestBody: UpdateToDoRequest) {
        viewModelScope.launch {
            myUpdateToDoResponse.postLoading()
            val response = todoupdate.updateToDoByRequest(id,requestBody)
            if(response.isSuccessful)
            {
                myUpdateToDoResponse.postSuccess(response)
            }
        }
    }

    private val tododelete = DeleteToDoUseCase(repository)

    val myDeleteToDoResponse : StateLiveData<Response<BaseResponse>> = StateLiveData()

    fun deleteToDo(id: Int?) {
        viewModelScope.launch {
            myDeleteToDoResponse.postLoading()
            val response = id?.let { tododelete.deleteTaskById(it) }
            if(response!!.isSuccessful)
            {
                myDeleteToDoResponse.postSuccess(response)
            }
        }
    }

}