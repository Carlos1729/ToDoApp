package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class SignUpOtpErrorResponse(
    @SerializedName("statusCode")
    val statusCode: Int = -1,
    @SerializedName("error")
    val errorMessage: String = "",
    @SerializedName("isAuthenticated")
    val isAuthenticated: Boolean = false
)
