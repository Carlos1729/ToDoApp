package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class BaseAddToDoResponse(
    @SerializedName("author_id")
    val authorId: Int?,
    @SerializedName("author_name")
    val authorName: String?,
    @SerializedName("content")
    val description: String?,
    @SerializedName("createdAt")
    val creationTime: String?,
    @SerializedName("id")
    val taskId: Int?,
    @SerializedName("modifiedAt")
    val lastModificationTime: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("priority")
    val priority: String,
    @SerializedName("status")
    val status: String?
)
