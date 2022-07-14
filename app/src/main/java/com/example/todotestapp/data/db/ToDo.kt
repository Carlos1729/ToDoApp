package com.example.todotestapp.data.db

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToDo(
    val id: Int,
    val taskId: Int,
    val title: String,
    val description: String,
): Parcelable



