package com.example.temax.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.temax.classes.RoomEntity

@Dao
interface RoomDao {

    //Inserção de uma lista de quartos
    @Insert
    fun insertAllRooms(rooms: List<RoomEntity>)

    //Obtem, todos os quartos
    @Query("SELECT * FROM room")
    fun getRooms(): List<RoomEntity>

    //Da delete de todos os quartos
    @Query("DELETE FROM room")
    fun deleteAllRooms()
}