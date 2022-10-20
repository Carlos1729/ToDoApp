package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class LoginOTPRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("isSignup")
    val isSignup: Boolean?,
)
