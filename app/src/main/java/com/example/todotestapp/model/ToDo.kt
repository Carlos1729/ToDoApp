package com.example.todotestapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ToDo(
    val id: Int,
    val taskId: Int,
    val title: String,
    val description: String,
): Parcelable
