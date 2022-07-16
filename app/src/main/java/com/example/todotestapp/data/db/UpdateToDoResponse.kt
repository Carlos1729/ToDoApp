package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class UpdateToDoResponse (

    @SerializedName("task")
    val task: BaseAddToDoResponse

    ):BaseResponse()