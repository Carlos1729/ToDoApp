package com.example.todotestapp.data.db

import androidx.annotation.Nullable

class StateData<T> {
    var status: DataStatus
        private set

    @get:Nullable
    @Nullable
    var data: T?
        private set

    @get:Nullable
    @Nullable
    var error: Throwable?
        private set

    fun loading(): StateData<T> {
        status = DataStatus.LOADING
        data = null
        error = null
        return this
    }

    fun success(data: T): StateData<T> {
        status = DataStatus.SUCCESS
        this.data = data
        error = null
        return this
    }

    fun error(error: Throwable): StateData<T> {
        status = DataStatus.ERROR
        data = null
        this.error = error
        return this
    }


    enum class DataStatus {
        CREATED, SUCCESS, ERROR, LOADING
    }

    init {
        status = DataStatus.CREATED
        data = null
        error = null
    }

}