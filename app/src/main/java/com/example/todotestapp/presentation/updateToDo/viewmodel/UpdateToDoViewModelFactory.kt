package com.example.todotestapp.presentation.updateToDo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel

class UpdateToDoViewModelFactory(private val repository: ToDoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UpdateToDoViewModel(repository) as T
        }
}