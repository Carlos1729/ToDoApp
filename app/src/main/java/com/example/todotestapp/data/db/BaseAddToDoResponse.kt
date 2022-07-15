package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class BaseAddToDoResponse(
    @SerializedName("authorId")
    val authorId: Int?,
    @SerializedName("content")
    val description: String?,
    @SerializedName("createdAt")
    val creationTime: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val taskId: Int?,
    @SerializedName("modifiedAt")
    val lastModificationTime: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("status")
    val status: String?,
)
