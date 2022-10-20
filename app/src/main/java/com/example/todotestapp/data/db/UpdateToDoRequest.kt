package com.example.todotestapp.data.db

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class UpdateToDoRequest (
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val title: String?,
    @SerializedName("content")
    val description: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("priority")
    val priority: String

    )