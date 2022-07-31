package com.example.todotestapp.presentation.grantpermission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.usecase.*

class GrantPermissionViewModelFactory(private val addAdminUseCase: AddAdminUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GrantPermissionViewModel(addAdminUseCase) as T
    }
}

