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
import javax.inject.Inject

class UpdateToDoViewModel @Inject constructor(addToDoAppUseCase : AddToDoUseCase,
                                              updateToDouseCase : UpdateToDoUseCase,
                                              deleteToDoUseCase: DeleteToDoUseCase ) : ViewModel(){

    private val todoadd =addToDoAppUseCase
    private val todoupdate = updateToDouseCase
    private val tododelete = deleteToDoUseCase

    val myAddToDoResponse : StateLiveData<Response<AddToDoResponse>> = StateLiveData()
    val myUpdateToDoResponse : StateLiveData<Response<UpdateToDoResponse>> = StateLiveData()
    val myDeleteToDoResponse : StateLiveData<Response<BaseResponse>> = StateLiveData()

    fun addToDo(requestBody: AddToDoRequest) {
        viewModelScope.launch {
            myAddToDoResponse.postLoading()
            val response = todoadd.addToDoByRequest(requestBody)
            if(response.isSuccessful)
            {
                myAddToDoResponse.postSuccess(response)
            }
            if(response.code() == 400)
            {
                myAddToDoResponse.postSuccess(response)
            }
        }
    }

    fun updateToDo(id: Int?, requestBody: UpdateToDoRequest) {
        viewModelScope.launch {
            myUpdateToDoResponse.postLoading()
            val response = todoupdate.updateToDoByRequest(id,requestBody)
            if(response.isSuccessful)
            {
                myUpdateToDoResponse.postSuccess(response)
            }
            if(response.code() == 400)
            {
                myUpdateToDoResponse.postSuccess(response)
            }
        }
    }

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