package com.example.todotestapp.presentation.logIn.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserRequest
import com.example.todotestapp.data.db.SignUpUserResponse
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


//Here to make the view model of login we are extending ViewModel cLASS
class LoginViewModel @Inject constructor(  repository: ToDoRepository ,  signUpUseCase : SignUpUserUseCase , loginUseCase : LoginUserUseCase ) : ViewModel() {

    private val userlogin = loginUseCase
    private val userSignUp = signUpUseCase

    val myLoginResponse : MutableLiveData<Response<LoginResponse>> = MutableLiveData()//whernever there is change in data it sends that data back to active user
                                                                                     //This is not preferable in real time scenarios as it mutable live data gets exposed insted to outside

    val mySignupResponse : MutableLiveData<Response<SignUpUserResponse>> = MutableLiveData()

    fun loginUser(email : String) {
        viewModelScope.launch {
            val response  = userlogin.loginUserByEmail(email) //loginUserByEmail is a suspend function hence it has to be executed in a certain coroutine scope I might have used live data here
            myLoginResponse.value = response                     //The ViewModel class can modify it however it wants (e.g. a timer ViewModel). You would use MutableLiveData if you wanted to modify it outside of the ViewModel class
        }
    }

    fun signUpUser(requestBody: SignUpUserRequest) {
        viewModelScope.launch {
            val response = userSignUp.SignUpUserByDetails(requestBody)//as long as this view model is alive run this couroutine
            mySignupResponse.value = response
        }
    }

}




