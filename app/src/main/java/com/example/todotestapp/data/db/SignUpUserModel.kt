package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class SignUpUserModel(
    val id: Int,
    val email: String,
    @SerializedName("author")
    val username:String,
    val message:String,
    val statusCode:Int
)
