package com.example.todotestapp.presentation.logIn.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.SignUpUserRequest
import com.example.todotestapp.data.db.SignUpUserResponse
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
            val response  = userlogin.loginUserByEmail(email)
            myLoginResponse.value = response
        }
    }

    val mySignupResponse : MutableLiveData<Response<SignUpUserResponse>> = MutableLiveData()


    fun signUpUser(requestBody: SignUpUserRequest) {
        viewModelScope.launch {
            val response = userSignUp.SignUpUserByDetails(requestBody)
            mySignupResponse.value = response
        }
    }

//    val myLoginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
//
//    fun loginUser(email: String) {
//        viewModelScope.launch {
//            val response = userlogin.loginUserByEmail(email)
//            myLoginResponse.value = response
//        }
//    }


}

//    val myLoginResponse : MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
//
//    fun loginUser(email : String) {
//        viewModelScope.launch {
//            val response : BaseResponse<LoginResponse>? = userlogin.loginUserByEmail(email).body()
//            myLoginResponse.value = response
//        }
//    }

//    val mySignupResponse : MutableLiveData<BaseResponse<SignUpUserResponse>> = MutableLiveData()
//
//
//    fun signUpUser(requestBody: SignUpUserResponse) {
//        viewModelScope.launch {
//            val response : BaseResponse<SignUpUserResponse>? = userSignUp.signUpUserByDetails(requestBody).body()
//            mySignupResponse.value = response
//        }
//    }



