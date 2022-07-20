package com.example.todotestapp.di

import dagger.Binds

abstract class DomainModule {

    @Binds
    abstract fun bindsToDoRepository()
}