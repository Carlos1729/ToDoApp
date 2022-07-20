package com.example.todotestapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.example.todotestapp.data.repository.Constants
import dagger.Provides
import javax.inject.Singleton

class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application?): Context? {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@NonNull appContext :  Context) : SharedPreferences{
      return  appContext.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }


}