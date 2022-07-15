package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class AddToDoRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("name")
    val title: String?,
    @SerializedName("content")
    val description: String?,
    @SerializedName("status")
    val status: String?
)
