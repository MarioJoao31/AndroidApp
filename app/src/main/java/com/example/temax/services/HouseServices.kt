package com.example.temax.services

import com.example.temax.classes.CreateHouse
import com.example.temax.classes.House
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HouseServices {

    @GET("/house")
    fun getAllHouses(): Call<List<House>>

    //PROTOCOL: Get
    // ROTA: /house/rentHouses
    // DESC: Retorna todas as casas disponíveis para aluguer
    @GET("/house/rentHouses")
    fun getRentHouses(): Call<List<House>>
    //tem de passar um createhouse nao uma house
    @POST("/house/createHouse")
    fun createHouse(@Body house: CreateHouse): Call<CreateHouse>
}