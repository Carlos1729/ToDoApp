package com.example.todotestapp.data.db

data class BaseResponse<T>(

    val statusCode: Int = -1,
    val errorMessage: String = "",
    val message: String = ""

)
