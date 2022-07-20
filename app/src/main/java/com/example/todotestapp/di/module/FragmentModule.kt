package com.example.todotestapp.di.module

import androidx.recyclerview.widget.ListAdapter
import com.example.todotestapp.presentation.listToDo.ui.ListToDoAdapter
import dagger.Provides
import javax.inject.Singleton

class FragmentModule {

    @Provides
    @Singleton
    fun provideToDoListAdapter() : ListToDoAdapter{
        return ListToDoAdapter()
    }


}