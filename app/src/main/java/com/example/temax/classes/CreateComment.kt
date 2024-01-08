package com.example.temax.classes

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class CreateComment (
                     @SerializedName("UserID") val userID: Int,
                     @SerializedName("Coment_Text") val commentText: String,
                     @SerializedName("Coment_Datetime") val commentDatetime: Timestamp,
                     @SerializedName("RoomID") val roomID: Int,
                     @SerializedName("HouseID") val houseID: Int,
                     @SerializedName("ApartmentID") val apartmentID: Int) {

}