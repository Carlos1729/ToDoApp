package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class SignUpOTPResponse(
    @SerializedName("author")
    var author : UserDetails?,
):BaseLoginOTPResponse()
