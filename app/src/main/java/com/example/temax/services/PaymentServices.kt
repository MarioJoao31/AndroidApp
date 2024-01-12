package com.example.temax.services

import com.example.temax.classes.CreatePayment
import com.example.temax.classes.Payment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentServices {

    //PROTOCOL: Get
    // ROTA: /Payment
    // DESC: Retorna todos os pagamentos existentes
    @GET("/payment")
    fun findAll(): Call<List<Payment>>

    @GET("/payment/{userID}")
    fun getUserPayPayment(@Path("userID") userID: Int): Call<List<Payment>>

    //PROTOCOL: Post
    // ROTA: /payment/createPayment
    // DESC: Cria um novo pagamento associado a um User
    @POST("/payment/createPayment")
    //Criamos classe CreatePayment para criarmos o pagamento sem o id.
    fun createPayment(@Body payment: CreatePayment): Call<CreatePayment>

}