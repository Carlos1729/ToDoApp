package com.example.todotestapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private var _emailIDOfUser = MutableLiveData<String>()
    val emailIDOfUser : LiveData<String> = _emailIDOfUser

    fun saveEmailIDOfUser(newEmailIDOfUser: String)
    {
        _emailIDOfUser.value = newEmailIDOfUser
    }
}