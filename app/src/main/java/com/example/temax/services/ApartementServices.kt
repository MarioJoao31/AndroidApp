package com.example.temax.services

import com.example.temax.classes.Apartement
import com.example.temax.classes.CreateApartement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApartementServices {

    //PROTOCOL: Get
    // ROTA: /apartement
    // DESC: Retorna todos os apartamentos disponíveis
    @GET("/apartement")
    fun getAllApartements(): Call<List<Apartement>>


    //PROTOCOL: Get
    // ROTA: /apartement/{userID}
    // DESC: Retorna todos os apartamentos do user
    @GET("/apartement/{userID}")
    fun getUserApartments(@Path("userID") userID: Int): Call<List<Apartement>>

    //PROTOCOL: Get
    // ROTA: /apartement/rentApartements
    // DESC: Retorna todas os apartamentos disponíveis para aluguel
    @GET("/apartement/rentApartements")
    fun getRentApartements(): Call<List<Apartement>>

    //PROTOCOL: Get
    // ROTA: /apartement/sellApartements
    // DESC: Retorna todas os apartamentos disponíveis para venda
    @GET("/apartement/sellApartements")
    fun getSellApartements(): Call<List<Apartement>>

    //PROTOCOL: Post
    // ROTA: /apartement/createApartement
    // DESC: Cria apartamento
    @POST("/apartement/createApartement")
    //Criamos classe CreateApartement para criarmos o apartamento sem o id.
    fun createApartement(@Body apartement: CreateApartement): Call<CreateApartement>
}