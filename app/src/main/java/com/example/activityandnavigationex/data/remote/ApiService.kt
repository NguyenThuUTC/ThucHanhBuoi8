package com.example.activityandnavigationex.data.remote

import com.example.activityandnavigationex.data.model.LoginRequest
import com.example.activityandnavigationex.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse//alt+enter

//    @GET("user/{userId}")
//    suspend fun getUser(@Path("userId") userId: String): UserResponse

}