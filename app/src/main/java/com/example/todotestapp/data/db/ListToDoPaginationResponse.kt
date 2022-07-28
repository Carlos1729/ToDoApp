package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class ListToDoPaginationResponse(
    @SerializedName("tasks")
    val tasks: List<BaseListToDoResponse>?,
    @SerializedName("totalPage")
    val totalPage: Int?
):BaseResponse()
