package com.example.temax.classes

import com.google.gson.annotations.SerializedName

class CreateRoom(
                @SerializedName("UserID") val UserID: Int,
                @SerializedName("Price") val Price: Double,
                @SerializedName("Construction_year") val Construction_year: Int,
                @SerializedName("Parking") val Parking: Int,
                @SerializedName("Elevator") val Elevator: String,
                @SerializedName("Prioraty_level") val Prioraty_level: Int,
                @SerializedName("Description") val Description: String,
                @SerializedName("Postal_code") val Postal_code: String,
                @SerializedName("Num_beds") val Num_beds: Int,
                @SerializedName("Private_wc") val Private_wc: Int,
                @SerializedName("Available_kitchen") val Available_kitchen: String,
                @SerializedName("ListingType") val ListingType: String,
                @SerializedName("Shared_room") val Shared_room: String,
                @SerializedName("Title") val Title: String,
                @SerializedName("Address") val Address: String
)