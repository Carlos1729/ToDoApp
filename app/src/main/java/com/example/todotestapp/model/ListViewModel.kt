package com.example.todotestapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.network.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: Repository) : ViewModel(){

    val mytasks : MutableLiveData<Response<List<ToDo>>> = MutableLiveData()
    val myresponse : MutableLiveData<Response<ToDo>> = MutableLiveData()

    fun pushTask(post: ToDo){
        viewModelScope.launch {
            val response : Response<ToDo> = repository.pushTask(post)
            myresponse.value = response
        }
    }
    fun getTask(id: Int){
        viewModelScope.launch {
            val response : Response<List<ToDo>> = repository.getTask(id)
            mytasks.value = response
        }
    }
}