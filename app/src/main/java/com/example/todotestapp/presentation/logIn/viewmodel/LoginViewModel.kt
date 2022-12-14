package com.example.todotestapp.presentation.logIn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.usecase.LoginUserByOTPUseCase
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserByOTPUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


//Here to make the view model of login we are extending ViewModel cLASS
class LoginViewModel @Inject constructor(signUpUseCase : SignUpUserUseCase , loginUseCase : LoginUserUseCase, loginUserByOTPUseCase: LoginUserByOTPUseCase, signUpUserByOTPUseCase: SignUpUserByOTPUseCase ) : ViewModel() {

    private val userlogin = loginUseCase
    private val userSignUp = signUpUseCase
    private val userLoginByOTP = loginUserByOTPUseCase
    private val userSignUpByOTP = signUpUserByOTPUseCase

    val myLoginResponse :StateLiveData<Response<LoginResponse>>? = StateLiveData()//whernever there is change in data it sends that data back to active user
                                                                                     //This is not preferable in real time scenarios as it mutable live data gets exposed insted to outside
    val mySignupResponse :  StateLiveData<Response<SignUpUserResponse>> = StateLiveData()

    val myLoginUserByOTPResponse : StateLiveData<Response<LoginOTPResponse>> = StateLiveData()

    val mySignUpUserByOTPResponse : StateLiveData<Response<SignUpOTPResponse>> = StateLiveData()

    fun loginUser(email : String) {

            viewModelScope.launch {

                myLoginResponse?.postLoading()
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

        }

    fun loginUserByOTP(requestBody: LoginOTPRequest){
        viewModelScope.launch {
            myLoginUserByOTPResponse.postLoading()
            val response = userLoginByOTP.LoginUserByOTPEmail(requestBody)
            if(response.isSuccessful)
            {
                myLoginUserByOTPResponse.postSuccess(response)
            }
            else if(response.code() == 400) {
                myLoginUserByOTPResponse.postSuccess(response)
            }
        }
    }

    fun signUpUser(requestBody: SignUpUserRequest) {
        viewModelScope.launch {
            mySignupResponse.postLoading()
            val response = userSignUp.SignUpUserByDetails(requestBody)//as long as this view model is alive run this couroutine
            if(response.isSuccessful)
            {
                mySignupResponse.postSuccess(response)
            }
            else if(response.code() == 400)
            {
                mySignupResponse.postSuccess(response)
            }
            else if(response.code() == 400)
            {
                mySignupResponse.postSuccess(response)
            }
        }
    }

    fun signUpUserByOTP(requestBody: SignUpOTPRequest){
        viewModelScope.launch {
            mySignUpUserByOTPResponse.postLoading()
            val response = userSignUpByOTP.SignUpUserByOTPEmail(requestBody)
            if(response.isSuccessful)
            {
                mySignUpUserByOTPResponse.postSuccess(response)
            }
            else if(response.code() == 400) {
                mySignUpUserByOTPResponse.postSuccess(response)
            }
        }
    }

}




