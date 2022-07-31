package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class AddAdminRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("isAdmin")
    val isAdmin: Boolean?,
)
