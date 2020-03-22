package com.rafaelmfer.marvelcharactersconsultation.model.comic


import com.google.gson.annotations.SerializedName

data class TextObject(
    @SerializedName("type") val type: String,
    @SerializedName("language") val language: String,
    @SerializedName("text") val text: String
)