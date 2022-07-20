package com.example.todotestapp.presentation.logIn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(private val userlogin : LoginUserUseCase, private val userSignUp : SignUpUserUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(userlogin, userSignUp) as T
    }
}