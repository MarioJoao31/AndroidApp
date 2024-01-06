package com.example.temax.services

import Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentService {
    @GET("/comments")
    fun getAllComments(): Call<List<Comment>>

    @GET("/comments/house/{HouseID}")
    fun getCommentsByHouseID(@Path("HouseID") HouseID: Int): Call<List<Comment>>

    // Endpoint para obter comentários por ApartmentID
    @GET("/comments/apartment/{ApartmentID}")
    fun getCommentsByApartmentID(@Path("ApartmentID") ApartmentID: Int): Call<List<Comment>>
}