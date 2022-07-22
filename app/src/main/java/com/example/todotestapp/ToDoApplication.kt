package com.example.todotestapp


import com.example.todotestapp.di.compoment.ApplicationComponent
import com.example.todotestapp.di.compoment.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ToDoApplication : DaggerApplication() {

    lateinit var  appComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerApplicationComponent.factory().create(this)
        return appComponent
    }

}