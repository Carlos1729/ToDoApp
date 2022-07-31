package com.example.todotestapp.presentation.grantpermission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotestapp.data.db.*
import com.example.todotestapp.domain.usecase.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject



class GrantPermissionViewModel @Inject constructor(addAdminUseCase: AddAdminUseCase) : ViewModel(){

    private val addAdmin = addAdminUseCase
    val myAddAdminResponse : StateLiveData<Response<AddAdminResponse>> = StateLiveData()

    fun addAdminFun(id: Int?, requestBody: AddAdminRequest) {
        viewModelScope.launch {
            myAddAdminResponse.postLoading()
            val response = addAdmin.addAdminByRequest(id,requestBody)
            if(response.isSuccessful)
            {
                myAddAdminResponse.postSuccess(response)
            }
            if(response.code() == 404)
            {
                myAddAdminResponse.postSuccess(response)
            }
        }
    }

}


