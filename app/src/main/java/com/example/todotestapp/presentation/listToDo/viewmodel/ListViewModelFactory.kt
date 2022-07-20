package com.example.todotestapp.presentation.listToDo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.DeleteToDoUseCase
import com.example.todotestapp.domain.usecase.ListToDoByStatusUseCase
import com.example.todotestapp.domain.usecase.ListToDoUseCase
import javax.inject.Inject

class ListViewModelFactory @Inject constructor(private val todofetch : ListToDoUseCase, private val todostatusfetch : ListToDoByStatusUseCase, private val tododelete : DeleteToDoUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(todofetch, todostatusfetch, tododelete) as T
    }
}

