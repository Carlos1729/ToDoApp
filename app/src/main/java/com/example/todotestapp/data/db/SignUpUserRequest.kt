package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class SignUpUserRequest (

    @SerializedName("email")
    var email: String?,
    @SerializedName("name")
    val name: String?

    )