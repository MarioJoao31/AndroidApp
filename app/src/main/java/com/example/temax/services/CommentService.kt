package com.example.temax.services

import Comment
import com.example.temax.CommentsScreen
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @GET("/comments")
    fun getAllComments(): Call<List<Comment>>

    @GET("/comments/house/{HouseID}")
    fun getCommentsByHouseID(@Path("HouseID") HouseID: Int): Call<List<Comment>>

    // Endpoint para obter comentários por ApartmentID
    @GET("/comments/apartment/{ApartmentID}")
    fun getCommentsByApartmentID(@Path("ApartmentID") ApartmentID: Int): Call<List<Comment>>

    @GET("/comments/room/{RoomID}")
    fun getCommentsByRoomID(@Path("RoomID") RoomID: Int): Call<List<Comment>>

    // Endpoint para enviar um novo comentário
    @POST("/comments/createComment") // Verifique e ajuste o endpoint conforme necessário
    fun createComment(@Body comment: CommentsScreen.CreateCommentData): Call<Comment> // Mudança no tipo de retorno para Call<Comment>
}