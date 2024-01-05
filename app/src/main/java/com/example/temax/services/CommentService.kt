package com.example.temax.services

import Comment
import retrofit2.Call
import retrofit2.http.GET

interface CommentService {
    @GET("/comments")
    fun getAllComments(): Call<List<Comment>>
}