package com.example.todotestapp.presentation.updateToDo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.AddToDoRequest
import com.example.todotestapp.data.db.AddToDoResponse
import com.example.todotestapp.data.db.UpdateToDoRequest
import com.example.todotestapp.data.db.UpdateToDoResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.AddToDoUseCase
import com.example.todotestapp.domain.usecase.UpdateToDoUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class UpdateToDoViewModel(private val repository: ToDoRepository) : ViewModel(){

    private val todoadd = AddToDoUseCase(repository)

    val myAddToDoResponse : MutableLiveData<Response<AddToDoResponse>> = MutableLiveData()

    fun addToDo(requestBody: AddToDoRequest) {
        viewModelScope.launch {
            val response = todoadd.addToDoByRequest(requestBody)
            myAddToDoResponse.value = response
        }
    }

    private val todoupdate = UpdateToDoUseCase(repository)

    val myUpdateToDoResponse : MutableLiveData<Response<UpdateToDoResponse>> = MutableLiveData()

    fun updateToDo(id: Int?, requestBody: UpdateToDoRequest) {
        viewModelScope.launch {
            val response = todoupdate.updateToDoByRequest(id,requestBody)
            myUpdateToDoResponse.value = response
        }
    }

}