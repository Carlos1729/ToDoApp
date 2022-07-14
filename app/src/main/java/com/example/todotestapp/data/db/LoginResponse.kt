package com.example.todotestapp.data.db

data class LoginResponse(
    val message: String,
    val statusCode: Int,
    val user: User
)
