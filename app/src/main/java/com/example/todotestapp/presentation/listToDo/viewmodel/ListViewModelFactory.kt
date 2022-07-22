package com.example.todotestapp.presentation.listToDo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.*

class ListViewModelFactory(private val listToDoUseCase: ListToDoUseCase,
                           private val listToDoByStatusUseCase: ListToDoByStatusUseCase,
                           private val deleteUseCase : DeleteToDoUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(listToDoUseCase,listToDoByStatusUseCase,deleteUseCase) as T
    }
}

