package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class ListToDoResponse (

    @SerializedName("tasks")
    val tasks: List<BaseListToDoResponse>?

    ):BaseResponse()