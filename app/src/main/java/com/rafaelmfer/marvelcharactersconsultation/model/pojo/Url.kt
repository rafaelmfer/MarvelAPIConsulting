package com.rafaelmfer.marvelcharactersconsultation.model.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Url(
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String
) : Parcelable