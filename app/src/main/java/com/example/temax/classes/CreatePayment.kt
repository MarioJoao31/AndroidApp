package com.example.temax.classes

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class CreatePayment(@SerializedName("UserID") val UserID: Int,
                    @SerializedName("Price") val Price: Double,
                    @SerializedName("Status") val Status: String,
                    @SerializedName("Type_Payment") val Type_Payment: String,
                    @SerializedName("Date") val Date: String) {
}