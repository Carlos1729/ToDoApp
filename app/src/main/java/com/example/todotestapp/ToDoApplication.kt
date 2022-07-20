package com.example.todotestapp

import android.app.Application
import com.example.todotestapp.di.compoment.DaggerApplicationComponent

class ToDoApplication : Application() {

    val appComponent = DaggerApplicationComponent.create();

    override fun onCreate() {
        super.onCreate()
    }

}