package com.example.todotestapp.data.repository

import com.example.todotestapp.data.api.ToDoApi
import com.example.todotestapp.data.repository.Constants.Companion.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()).addConverterFactory(GsonConverterFactory.create()).build()
    }

    //The Retrofit class generates an implementation of the ToDoAPIService interface.
    val api: ToDoApi by lazy {
        retrofit.create(ToDoApi::class.java)
    }

}