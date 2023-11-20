package com.example.temax.classes

import com.google.gson.annotations.SerializedName

class Apartement (@SerializedName("ApartementID") val ApartementID: Int,
                  @SerializedName("UserID") val UserID: Int,
                  @SerializedName("Price") val Price: Double,
                  @SerializedName("Construction_year") val Construction_year: Int,
                  @SerializedName("Parking") val Parking: Int,
                  @SerializedName("Elevator") val Elevator: String,
                  @SerializedName("Prioraty_level") val Prioraty_level: Int,
                  @SerializedName("Description") val Description: String,
                  @SerializedName("Postal_code") val Postal_code: String,
                  @SerializedName("Floor") val Floor: Int,
                  @SerializedName("Bedrooms") val Bedrooms: Int,
                  @SerializedName("WCs") val WCs: Int,
                  @SerializedName("ListingType") val ListingType: String,
                  @SerializedName("Title") val Title: String,
                  @SerializedName("Address") val Address: String) {
}