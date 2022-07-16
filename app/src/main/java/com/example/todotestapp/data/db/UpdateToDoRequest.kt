package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class UpdateToDoRequest (
    @SerializedName("name")
    val title: String?,
    @SerializedName("content")
    val description: String?,
    @SerializedName("status")
    val status: String?
    )