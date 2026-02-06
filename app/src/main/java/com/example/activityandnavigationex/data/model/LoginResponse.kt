package com.example.activityandnavigationex.data.model

data class LoginResponse(
    val token: String? = null,
    val userId: String? = null,
    val message: String? = null
)