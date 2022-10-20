package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class SignUpUserResponse(

    @SerializedName("author")
    val author:UserDetails?

): BaseResponse()
