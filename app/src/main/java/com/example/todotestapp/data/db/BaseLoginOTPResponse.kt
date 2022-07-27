package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

open class BaseLoginOTPResponse(
    @SerializedName("statusCode")
    val statusCode: Int = -1,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("error")
    val error: String = "",
    @SerializedName("isAuthenticated")
    val isAuthenticated: Boolean = false
)
