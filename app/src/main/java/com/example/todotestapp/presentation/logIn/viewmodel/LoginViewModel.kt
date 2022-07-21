package com.example.todotestapp.presentation.logIn.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.R
import com.example.todotestapp.data.db.*
import com.example.todotestapp.data.repository.NetworkResult
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.UnknownHostException


//Here to make the view model of login we are extending ViewModel cLASS
class LoginViewModel (private val repository: ToDoRepository) : ViewModel() {

    private val userlogin = LoginUserUseCase(repository)
    private val userSignUp = SignUpUserUseCase(repository)



    var myLoginResponse : StateLiveData<Response<LoginResponse>>? = StateLiveData()

//    val myLoginResponse : MutableLiveData<NetworkResult<Response<LoginResponse>>> = MutableLiveData()//whernever there is change in data it sends that data back to active user


    //    val myLoginResponse : MutableLiveData<NetworkResult<Response<LoginResponse>>> = MutableLiveData()//whernever there is change in data it sends that data back to active user
                                                                                     //This is not preferable in real time scenarios as it mutable live data gets exposed insted to outside
    fun loginUser(email : String) {
        viewModelScope.launch {

            myLoginResponse?.postLoading()
            viewModelScope.launch {
                val response  = userlogin.loginUserByEmail(email) //loginUserByEmail is a suspend function hence it has to be executed in a certain coroutine scope I might have used live data here
                if(response.isSuccessful)
                {
                    myLoginResponse?.postSuccess(response)
                }
                else if(response.code() == 404)
                {
                    myLoginResponse?.postSuccess(response)
                }
                else if(response.code() == 400)
                {
                    myLoginResponse?.postSuccess(response)
                }
            }
            myLoginResponse?.postError(UnknownHostException())


        //        try {
//                Log.v("signUpFlagTest",response.body().toString())
////                if(response.body() != null)
////                {
//                    myLoginResponse.value = (NetworkResult.Success(response))                     //The ViewModel class can modify it however it wants (e.g. a timer ViewModel). You would use MutableLiveData if you wanted to modify it outside of the ViewModel class
//
//            }
//            catch (e:Exception)
//            {
//                myLoginResponse.value = (NetworkResult.Error(e.message.toString()))
//            }
        }
    }



    val mySignupResponse : MutableLiveData<Response<SignUpUserResponse>> = MutableLiveData()


    fun signUpUser(requestBody: SignUpUserRequest) {
        viewModelScope.launch {
            val response = userSignUp.SignUpUserByDetails(requestBody)//as long as this view model is alive run this couroutine
            mySignupResponse.value = response
        }
    }


}




