package com.rafaelmfer.marvelcharactersconsultation.model.comic


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("type") val type: String,
    @SerializedName("price") val price: Double
)