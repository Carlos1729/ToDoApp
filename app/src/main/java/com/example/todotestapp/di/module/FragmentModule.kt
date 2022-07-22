package com.example.todotestapp.di.module


import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.LoginUserUseCase
import com.example.todotestapp.domain.usecase.SignUpUserUseCase
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
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun getLoginFragment() : LoginFragment

    @ContributesAndroidInjector
    abstract fun getListToDoFragment() : ListToDoFragment

    @ContributesAndroidInjector
    abstract fun getUpdateFragment() : UpdateToDoFragment


    companion object{

        @Provides
        fun provideLoginUseCase(toDoRepository: ToDoRepository) = LoginUserUseCase(toDoRepository)

        @Provides
        fun provideSignUpuserCache(toDoRepository: ToDoRepository) = SignUpUserUseCase(toDoRepository)

        @Provides
        fun provideLoginViewModelFactory(toDoRepository: ToDoRepository,signUpUseCase: SignUpUserUseCase
                                         ,loginUseCase : LoginUserUseCase) : LoginViewModelFactory {
            return LoginViewModelFactory(toDoRepository,signUpUseCase,loginUseCase)
        }

        @Provides
         fun provideListViewModelFactory(toDoRepository: ToDoRepository) : ListViewModelFactory =  ListViewModelFactory(toDoRepository)

        @Provides
         fun provideUpdateToDoViewModelFactory(toDoRepository: ToDoRepository) = UpdateToDoViewModelFactory(toDoRepository)

    }

}