package com.example.todotestapp.di.module

import androidx.annotation.NonNull
import com.example.todotestapp.data.repository.Constants.BASE_URL
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.di.module.scopes.AppScoped
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideGsonFactory( gson : Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideRetrofitInstance(
        @NonNull okHttpClient: OkHttpClient,
        @NonNull gsonFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonFactory).build()
    }

}