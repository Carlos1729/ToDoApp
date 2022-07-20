package com.example.todotestapp.di

import com.example.todotestapp.presentation.MainActivity
import dagger.Component
import javax.inject.Inject


@Component(modules = [AppModule::class])
interface ApplicationComponent {
        fun inject(activity: MainActivity)
}