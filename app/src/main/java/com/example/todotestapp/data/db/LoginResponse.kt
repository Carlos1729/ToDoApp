package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("author")
    var author : UserDetails?,

    ) : BaseResponse()
