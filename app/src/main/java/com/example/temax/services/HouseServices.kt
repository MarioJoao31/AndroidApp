package com.example.temax.services

import com.example.temax.classes.CreateHouse
import com.example.temax.classes.House
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HouseServices {

    @GET("/house")
    fun getAllHouses(): Call<List<House>>


    //PROTOCOL: Get
    // ROTA: /house/changePrioraty/{userID}
    // DESC: Muda a prioridade de todas as casas do user.
    @GET("/house/changePrioraty/{userID}")
    fun updateHousePrioratyLevel(@Path("userID") userID: Int): Call<List<House>>

    //PROTOCOL: Get
    // ROTA: /house/{userID}
    // DESC: Retorna todas as casas do user
    @GET("/house/{userID}")
    fun getUserHouses(@Path("userID") userID: Int): Call<List<House>>

    //PROTOCOL: Get
    // ROTA: /house/rentHouses
    // DESC: Retorna todas as casas disponíveis para aluguer
    @GET("/house/rentHouses")
    fun getRentHouses(): Call<List<House>>

    //PROTOCOL: Get
    // ROTA: /house/SellHouses
    // DESC: Retorna todas as casas disponíveis para vender
    @GET("/house/sellHouses")
    fun getSellHouses(): Call<List<House>>
    //tem de passar um createhouse nao uma house
    @POST("/house/createHouse")
    fun createHouse(@Body house: CreateHouse): Call<CreateHouse>
}