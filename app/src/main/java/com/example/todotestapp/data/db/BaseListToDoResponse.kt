package com.example.todotestapp.data.db

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseListToDoResponse(
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
    val title: String?,
    @SerializedName("priority")
    val priority: String?,
    @SerializedName("status")
    val status: String?,
): Parcelable
