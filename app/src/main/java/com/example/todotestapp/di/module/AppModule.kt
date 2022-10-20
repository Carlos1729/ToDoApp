package com.example.todotestapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.example.todotestapp.data.repository.Constants
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule() {

    @Binds
    abstract fun provideContext(application: Application?): Context

    companion object {

        @Provides
        fun provideSharedPreferences(@NonNull appContext: Context): SharedPreferences {
            return appContext.getSharedPreferences(
                Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
        }

        @Provides
        fun provideToDoApiImpl(): ToDoRepository = ToDoRepositoryImpl()
    }


}