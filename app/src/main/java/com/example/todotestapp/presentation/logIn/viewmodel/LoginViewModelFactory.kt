package com.example.todotestapp.presentation.logIn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.LoginUserByOTPUseCase
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserByOTPUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase

class LoginViewModelFactory(private val signUpUseCase :  SignUpUserUseCase ,
                            private val loginUseCase : LoginUserUseCase,private val loginUserByOTPUseCase: LoginUserByOTPUseCase, private val signUpUserByOTPUseCase: SignUpUserByOTPUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(signUpUseCase,loginUseCase,loginUserByOTPUseCase,signUpUserByOTPUseCase) as T
    }
}