package com.example.todotestapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel : LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateToDoViewModel::class)
    abstract fun bindUpdateToDoViewModel(updateToDoViewModel : UpdateToDoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindListViewModel(listViewModel : ListViewModel): ViewModel

    companion object{
        @Provides
        fun provideLoginUseCase(toDoRepository: ToDoRepository) = LoginUserUseCase(toDoRepository)

        @Provides
        fun provideSignUpuserCache(toDoRepository: ToDoRepository) = SignUpUserUseCase(toDoRepository)
    }
}