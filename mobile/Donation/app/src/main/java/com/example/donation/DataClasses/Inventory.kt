package com.example.donation.DataClasses

import com.google.gson.annotations.SerializedName

data class Inventory(
    @SerializedName("A+") val APlus: Int,
    @SerializedName("B+") val BPlus: Int,
    @SerializedName("O+") val OPlus: Int,
    @SerializedName("AB+") val ABPlus: Int,
    @SerializedName("A-") val AMinus: Int,
    @SerializedName("B-") val BMinus: Int,
    @SerializedName("AB-") val ABMinus: Int,
    @SerializedName("O-") val OMinus: Int
)
