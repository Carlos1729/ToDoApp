package com.example.todotestapp.data.db

import com.google.gson.annotations.SerializedName

data class ListToDoPaginationResponse(
    @SerializedName("tasks")
    val tasks: List<ListToDoResponse>,
    @SerializedName("totalPage")
    val totalPage: Int?
):BaseResponse()
