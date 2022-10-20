package com.example.todotestapp.di.compoment

import android.app.Application
import com.example.todotestapp.ToDoApplication
import com.example.todotestapp.di.module.AppActivityBindingModule
import com.example.todotestapp.di.module.AppModule
import com.example.todotestapp.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [AppModule::class,
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppActivityBindingModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent : AndroidInjector<ToDoApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}