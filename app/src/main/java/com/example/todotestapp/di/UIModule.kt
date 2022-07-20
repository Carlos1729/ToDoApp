package com.example.todotestapp.di

import com.example.todotestapp.domain.usecase.*
import com.example.todotestapp.presentation.listToDo.ui.ListToDoFragment
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.example.todotestapp.presentation.logIn.ui.LoginFragment
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.example.todotestapp.presentation.updateToDo.ui.UpdateToDoFragment
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module
abstract class UIModule {

//    @ContributesAndroidInjector
//    abstract fun bindsListToDoFragment() : ListToDoFragment
//
//    @ContributesAndroidInjector
//    abstract fun bindsLoginFragment() : LoginFragment
//
//    @ContributesAndroidInjector
//    abstract fun bindsUpdateToDoFragment() : UpdateToDoFragment

    companion object{
        @Provides
        @JvmStatic
        fun provideListViewModelFactory(todofetch : ListToDoUseCase, todostatusfetch : ListToDoByStatusUseCase,tododelete : DeleteToDoUseCase) = ListViewModelFactory(todofetch, todostatusfetch, tododelete)
        @JvmStatic
        fun provideLoginViewModelFactory(userlogin : LoginUserUseCase, userSignUp : SignUpUserUseCase) = LoginViewModelFactory(userlogin, userSignUp)
        @JvmStatic
        fun provideUpdateToDoViewModelFactory(todoadd : AddToDoUseCase,todoupdate : UpdateToDoUseCase,tododelete : DeleteToDoUseCase) = UpdateToDoViewModelFactory(todoadd, todoupdate, tododelete)
    }

}