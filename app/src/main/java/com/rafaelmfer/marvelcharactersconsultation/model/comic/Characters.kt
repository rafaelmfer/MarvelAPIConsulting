package com.rafaelmfer.marvelcharactersconsultation.model.comic

import com.google.gson.annotations.SerializedName
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Item

data class Characters(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("returned") val returned: Int
)