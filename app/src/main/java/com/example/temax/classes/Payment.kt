package com.example.temax.classes

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

class Payment (@SerializedName("UserID") val UserID: Int,
               @SerializedName("PaymentID") val PaymentID: Int,
               @SerializedName("Price") val Price: Double,
               @SerializedName("Status") val Status: String,
               @SerializedName("Type_Payment") val Type_Payment: String,
               @SerializedName ("Date") val Date: String): Serializable{


}