package com.rafaelmfer.marvelcharactersconsultation.model.comic

import com.google.gson.annotations.SerializedName

data class ImageComic(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)