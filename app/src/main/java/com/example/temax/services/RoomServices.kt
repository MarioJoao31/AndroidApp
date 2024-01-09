package com.example.temax.services

import com.example.temax.classes.Room
import com.example.temax.classes.CreateRoom
import com.example.temax.classes.House
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomServices {

    //PROTOCOL: Get
    // ROTA: /room
    // DESC: Retorna todos os quartos disponíveis
    @GET("/room")
    fun getAllRooms(): Call<List<Room>>


    //PROTOCOL: Get
    // ROTA: /room/changePrioraty/{userID}
    // DESC: Muda a prioridade de todos os quartos do user.
    @GET("/room/changePrioraty/{userID}")
    fun updateRoomPrioratyLevel (@Path("userID") userID: Int): Call<List<Room>>

    //PROTOCOL: Get
    // ROTA: /room/{userID}
    // DESC: Retorna todos os quartos do user
    @GET("/room/{userID}")
    fun getUserRooms(@Path("userID") userID: Int): Call<List<Room>>

    //PROTOCOL: Get
    // ROTA: /room/rentRooms
    // DESC: Retorna todos os quartos disponíveis para aluguel
    @GET("/room/rentRooms")
    fun getRentRooms(): Call<List<Room>>

    //PROTOCOL: Get
    // ROTA: /room/sellRooms
    // DESC: Retorna todos os quartos disponíveis para venda
    @GET("/room/sellRooms")
    fun getSellRooms(): Call<List<Room>>

    //PROTOCOL: Post
    // ROTA: /room/createRoom
    // DESC: Cria quarto
    @POST("/room/createRoom")
    //Criamos classe CreateRoom para criarmos o quarto sem o id.
    fun createRoom(@Body room: CreateRoom): Call<CreateRoom>
}