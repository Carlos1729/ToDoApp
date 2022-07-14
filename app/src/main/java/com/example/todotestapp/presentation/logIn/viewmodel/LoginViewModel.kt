package com.example.todotestapp.presentation.logIn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserModel
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: ToDoRepository) : ViewModel() {

    private val userlogin = LoginUserUseCase(repository)
    private val userSignUp = SignUpUserUseCase(repository)


    val myLoginResponse : MutableLiveData<Response<LoginResponse>> = MutableLiveData()

    fun loginUser(email : String) {
        viewModelScope.launch {
            val response : Response<LoginResponse> = userlogin.loginUserByEmail(email)
            myLoginResponse.value = response
        }
    }

    val mySignupResponse : MutableLiveData<Response<SignUpUserModel>> = MutableLiveData()


    fun signUpUser(requestBody: SignUpUserModel) {
        viewModelScope.launch {
            val response : Response<SignUpUserModel> = userSignUp.signUpUserByDetails(requestBody)
            mySignupResponse.value = response
        }
    }

}

