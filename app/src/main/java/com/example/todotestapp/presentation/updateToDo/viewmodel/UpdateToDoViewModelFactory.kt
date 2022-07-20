package com.example.todotestapp.presentation.updateToDo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.AddToDoUseCase
import com.example.todotestapp.domain.usecase.DeleteToDoUseCase
import com.example.todotestapp.domain.usecase.UpdateToDoUseCase
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import javax.inject.Inject

class UpdateToDoViewModelFactory @Inject constructor(private val todoadd : AddToDoUseCase, private val todoupdate : UpdateToDoUseCase, private val tododelete : DeleteToDoUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UpdateToDoViewModel(todoadd, todoupdate, tododelete) as T
        }
}