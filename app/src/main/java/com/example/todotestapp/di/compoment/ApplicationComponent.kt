package com.example.todotestapp.di.compoment

import android.app.Application
import com.example.todotestapp.ToDoApplication
import com.example.todotestapp.di.module.AppActivityBindingModule
import com.example.todotestapp.di.module.AppModule
import com.example.todotestapp.di.module.DataModule
import com.example.todotestapp.di.module.ViewModelModule
import com.example.todotestapp.di.module.scopes.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton



@Component(
    modules = [DataModule::class,
        AppModule::class,
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