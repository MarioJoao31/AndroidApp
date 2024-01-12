package com.example.temax.services

import com.example.temax.classes.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{userID}/name")
    fun getUserName(@Path("userID") userID: Int): Call<String>
}


