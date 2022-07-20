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

class UpdateToDoViewModel( private val todoadd : AddToDoUseCase, private val todoupdate : UpdateToDoUseCase, private val tododelete : DeleteToDoUseCase) : ViewModel(){


    val myAddToDoResponse : MutableLiveData<Response<AddToDoResponse>> = MutableLiveData()

    fun addToDo(requestBody: AddToDoRequest) {
        viewModelScope.launch {
            val response = todoadd.addToDoByRequest(requestBody)
            myAddToDoResponse.value = response
        }
    }


    val myUpdateToDoResponse : MutableLiveData<Response<UpdateToDoResponse>> = MutableLiveData()

    fun updateToDo(id: Int?, requestBody: UpdateToDoRequest) {
        viewModelScope.launch {
            val response = todoupdate.updateToDoByRequest(id,requestBody)
            myUpdateToDoResponse.value = response
        }
    }


    val myDeleteToDoResponse : MutableLiveData<Response<BaseResponse>> = MutableLiveData()

    fun deleteToDo(id: Int?) {
        viewModelScope.launch {
            val response = id?.let { tododelete.deleteTaskById(it) }
            myDeleteToDoResponse.value = response
        }
    }

}