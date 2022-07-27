package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("email")
    val email: String = "",
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("role")
    val role: String = ""
)
