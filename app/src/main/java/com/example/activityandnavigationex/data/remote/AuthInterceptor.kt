package com.example.activityandnavigationex.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer token....")
            .addHeader("Accept", "application/json")
            .build()

        Log.d("AuthInterceptor", "Added Authorization header")

        // before send REQUEST
        Log.d("AuthInterceptor", "Sending request: ${newRequest.url}")
        Log.d("AuthInterceptor", "Method: ${newRequest.method}")


        val startTime = System.currentTimeMillis()

        val response = chain.proceed(newRequest) // send request

        // after received RESPONSE
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime

        Log.d(
            "AuthInterceptor",
            "Received response for ${response.request.url} in ${duration}ms"
        )
        Log.d("AuthInterceptor", "Status code: ${response.code}")

        return response
    }
}