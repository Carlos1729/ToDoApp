package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class SignUpUserModel(

    @SerializedName("author")
    val userResponse:LoginResponse

)
