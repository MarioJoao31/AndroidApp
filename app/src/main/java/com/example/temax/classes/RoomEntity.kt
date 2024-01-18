package com.example.temax.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Especifica que esta classe representa uma entidade no Room e define o nome da tabela como "room".
@Entity(tableName = "room")
data class RoomEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0, //id é a chave primaria
    @ColumnInfo(name = "RoomID") val RoomID: Int,
    @ColumnInfo(name = "UserID") val UserID: Int,
    @ColumnInfo(name = "Price") val Price: Double,
    @ColumnInfo(name = "Construction_year") val Construction_year: Int,
    @ColumnInfo(name = "Parking") val Parking: Int,
    @ColumnInfo(name = "Elevator") val Elevator: String,
    @ColumnInfo(name = "Prioraty_level") val Prioraty_level: Int,
    @ColumnInfo(name = "Description") val Description: String,
    @ColumnInfo(name = "Postal_code") val Postal_code: String,
    @ColumnInfo(name = "Num_beds") val Num_beds: Int,
    @ColumnInfo(name = "Private_wc") val Private_wc: Int,
    @ColumnInfo(name = "Available_kitchen") val Available_kitchen: String,
    @ColumnInfo(name = "ListingType") val ListingType: String,
    @ColumnInfo(name = "Shared_room") val Shared_room: String,
    @ColumnInfo(name = "Title") val Title: String,
    @ColumnInfo(name = "Address") val Address: String)