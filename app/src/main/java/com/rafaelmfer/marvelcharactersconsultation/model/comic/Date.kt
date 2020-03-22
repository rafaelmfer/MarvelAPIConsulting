package com.rafaelmfer.marvelcharactersconsultation.model.comic

import com.google.gson.annotations.SerializedName

data class Date(
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String
)