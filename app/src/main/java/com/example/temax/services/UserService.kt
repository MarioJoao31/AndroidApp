package com.example.temax.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserService {
    @GET("users/{userID}/name")
    fun getUserName(@Path("userID") userID: Int): Call<String>

    // Altere a assinatura do método updateProfile na interface UserService
    @PATCH("users/{userID}/update")
    fun updateProfile(@Path("userID") userID: Int, @Body updateData: MutableMap<String, String?>): Call<Any>

}


