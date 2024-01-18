package com.example.temax.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Especifica que esta classe representa uma entidade no Room e define o nome da tabela como "house".
@Entity(tableName = "house")
data class HouseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, //Indica que id é a chave primária da tabela e será gerada automaticamente.
    @ColumnInfo(name = "HouseID") val houseID: Int,
    @ColumnInfo(name = "UserID") val userID: Int,
    @ColumnInfo(name = "Price") val price: Double,
    @ColumnInfo(name = "Construction_year") val constructionYear: Int,
    @ColumnInfo(name = "Parking") val parking: Int,
    @ColumnInfo(name = "Elevator") val elevator: String,
    @ColumnInfo(name = "Prioraty_level") val priorityLevel: Int,
    @ColumnInfo(name = "Description") val description: String,
    @ColumnInfo(name = "Postal_code") val postalCode: String,
    @ColumnInfo(name = "Private_gross_area") val privateGrossArea: Int,
    @ColumnInfo(name = "Total_lot_area") val totalLotArea: Int,
    @ColumnInfo(name = "Bedrooms") val bedrooms: Int,
    @ColumnInfo(name = "WCs") val wcs: Int,
    @ColumnInfo(name = "ListingType") val listingType: String,
    @ColumnInfo(name = "Title") val title: String,
    @ColumnInfo(name = "Address") val address: String
)
