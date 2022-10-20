package com.example.todotestapp.di.module

import com.example.todotestapp.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppActivityBindingModule {

    @ContributesAndroidInjector( modules = [
        FragmentModule::class,
        ViewModelModule::class
    ])
    abstract fun getLoginActivity() : MainActivity

}