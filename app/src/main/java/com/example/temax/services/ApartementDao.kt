package com.example.temax.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.temax.classes.ApartementEntity

@Dao
interface ApartementDao {

    //Inserção de uma lista de apartamentos
    @Insert
    fun insertAllApartements(apartements: List<ApartementEntity>)

    //Obtem, todos os apartamentos
    @Query("SELECT * FROM apartement")
    fun getApartementsForRent(): List<ApartementEntity>

    //Da delete de todos os apartamentos
    @Query("DELETE FROM apartement")
    fun deleteAllApartements()
}