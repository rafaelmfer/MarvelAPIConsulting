package com.rafaelmfer.marvelcharactersconsultation.model.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(
    @SerializedName("type") val type: String,
    @SerializedName("price") val price: Double
) : Parcelable