package com.example.todotestapp.di

import com.example.todotestapp.data.api.ToDoApi
import com.example.todotestapp.data.repository.Constants
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesToDoAPI(retrofit: Retrofit) : ToDoApi{
        return retrofit.create(ToDoApi::class.java)
    }

}