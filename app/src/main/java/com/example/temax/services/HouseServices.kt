package com.example.temax.services

import com.example.temax.classes.House
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HouseServices {

    @GET("/house")
    fun getAllHouses(): Call<List<House>>

    @POST("/house/createHouse")
    fun createHouse(@Body house: House): Call<House>
}