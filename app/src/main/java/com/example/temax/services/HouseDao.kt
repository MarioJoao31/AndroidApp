package com.example.temax.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.temax.classes.HouseEntity

@Dao
interface HouseDao {

    // Inserção de uma lista de casas
    @Insert
    fun insertAllHouses(houses: List<HouseEntity>)

    //Obtem todas as casas
    @Query("SELECT * FROM house")
    fun getHousesForRent(): List<HouseEntity>

    //Da delete de todas as casas
    @Query("DELETE FROM house")
    fun deleteAllHouses()
}