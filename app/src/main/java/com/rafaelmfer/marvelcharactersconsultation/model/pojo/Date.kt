package com.rafaelmfer.marvelcharactersconsultation.model.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Date(
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String
) : Parcelable