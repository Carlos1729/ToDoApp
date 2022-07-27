package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class SignUpOTPRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("isSignup")
    val isSignup: Boolean?,
)
