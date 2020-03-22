package com.rafaelmfer.marvelcharactersconsultation.model.pojo

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String,
    @SerializedName("type") val type: String
)