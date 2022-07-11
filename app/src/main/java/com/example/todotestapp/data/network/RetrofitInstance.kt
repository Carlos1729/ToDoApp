package com.example.todotestapp.data.network

import com.example.todotestapp.data.network.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: ToDoApi by lazy {
        retrofit.create(ToDoApi::class.java)
    }
}