package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class AddAdminResponse(
    @SerializedName("author")
    var author : UserDetails?,
):BaseLoginOTPResponse()