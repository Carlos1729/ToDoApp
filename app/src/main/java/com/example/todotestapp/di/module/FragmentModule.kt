package com.example.todotestapp.di.module


import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.*
import com.example.todotestapp.presentation.grantpermission.ui.GrantPermissionsFragment
import com.example.todotestapp.presentation.grantpermission.viewmodel.GrantPermissionViewModelFactory
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
    abstract fun getLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun getListToDoFragment(): ListToDoFragment

    @ContributesAndroidInjector
    abstract fun getUpdateFragment(): UpdateToDoFragment

    @ContributesAndroidInjector
    abstract fun getGrantPermissionsFragment() : GrantPermissionsFragment


    companion object {

        @Provides
        fun provideLoginUseCase(toDoRepository: ToDoRepository) = LoginUserUseCase(toDoRepository)

        @Provides
        fun provideLoginUserByOTPUseCase(toDoRepository: ToDoRepository) = LoginUserByOTPUseCase(toDoRepository)

        @Provides
        fun provideSignUpUserByOTPUseCase(toDoRepository: ToDoRepository) = SignUpUserByOTPUseCase(toDoRepository)

        @Provides
        fun provideSignUpuserCache(toDoRepository: ToDoRepository) =
            SignUpUserUseCase(toDoRepository)

        @Provides
        fun providesAddAdminUseCase(toDoRepository: ToDoRepository) = AddAdminUseCase(toDoRepository)

        @Provides
        fun provideListToDoUseCase(toDoRepository: ToDoRepository) = ListToDoUseCase(toDoRepository)

        @Provides
        fun provideListToDoPaginationUseCase(toDoRepository: ToDoRepository) = ListToDoPaginationUseCase(toDoRepository)

        @Provides
        fun provideListToDoByStatusUseCase(toDoRepository: ToDoRepository) =
            ListToDoByStatusUseCase(toDoRepository)

        @Provides
        fun provideDeleteUseCase(toDoRepository: ToDoRepository) = DeleteToDoUseCase(toDoRepository)

        @Provides
        fun provideAddToDoUseCase(toDoRepository: ToDoRepository) = AddToDoUseCase(toDoRepository)

        @Provides
        fun provideUpdateToDoUseCase(toDoRepository: ToDoRepository) =
            UpdateToDoUseCase(toDoRepository)

        @Provides
        fun provideUpdateToDoInListUseCase(toDoRepository: ToDoRepository) = UpdateToDoInListUseCase(toDoRepository)

        @Provides
        fun provideLoginViewModelFactory(
            signUpUseCase: SignUpUserUseCase, loginUseCase: LoginUserUseCase,loginUserByOTPUseCase: LoginUserByOTPUseCase,signUpUserByOTPUseCase: SignUpUserByOTPUseCase
        ): LoginViewModelFactory {
            return LoginViewModelFactory(signUpUseCase, loginUseCase, loginUserByOTPUseCase,signUpUserByOTPUseCase)
        }

        @Provides
        fun provideGrantPermissionViewModelFactory(addAdminUseCase: AddAdminUseCase
            ): GrantPermissionViewModelFactory {
            return GrantPermissionViewModelFactory(addAdminUseCase)
        }

        @Provides
        fun provideListViewModelFactory(
            toDoListUseCase: ListToDoUseCase,
            toListByStatusUseCase: ListToDoByStatusUseCase,
            deleteToDoUseCase: DeleteToDoUseCase, listToDoPaginationUseCase: ListToDoPaginationUseCase, updateToDoInListUseCase: UpdateToDoInListUseCase
        ): ListViewModelFactory {
            return ListViewModelFactory(toDoListUseCase, toListByStatusUseCase, deleteToDoUseCase,listToDoPaginationUseCase,updateToDoInListUseCase)
        }

        @Provides
        fun provideUpdateToDoViewModelFactory(
            addToDoAppUseCase: AddToDoUseCase,
            updateToDouseCase: UpdateToDoUseCase,
            deleteToDoUseCase: DeleteToDoUseCase
        ) = UpdateToDoViewModelFactory(
            addToDoAppUseCase,
            updateToDouseCase, deleteToDoUseCase
        )

    }

}