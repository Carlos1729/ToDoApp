package com.example.todotestapp.presentation.updateToDo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.AddToDoUseCase
import com.example.todotestapp.domain.usecase.DeleteToDoUseCase
import com.example.todotestapp.domain.usecase.UpdateToDoUseCase
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel

class UpdateToDoViewModelFactory(private val addToDoAppUseCase : AddToDoUseCase,
                                 private val updateToDouseCase : UpdateToDoUseCase,
                                 private val deleteToDoUseCase: DeleteToDoUseCase
) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UpdateToDoViewModel(addToDoAppUseCase,updateToDouseCase,deleteToDoUseCase) as T
        }
}