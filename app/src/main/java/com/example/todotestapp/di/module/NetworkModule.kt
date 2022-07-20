package com.example.todotestapp.di.module

import androidx.annotation.NonNull
import com.example.todotestapp.data.repository.Constants.BASE_URL
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideToDoApiImpl() : ToDoRepository = ToDoRepositoryImpl()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        @NonNull okHttpClient: OkHttpClient,
        @NonNull gsonFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonFactory).build()
    }

}