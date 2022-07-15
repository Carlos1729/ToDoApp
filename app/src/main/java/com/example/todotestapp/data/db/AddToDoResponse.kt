package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class AddToDoResponse (

    @SerializedName("task")
    val task: BaseAddToDoResponse

    ): BaseResponse()