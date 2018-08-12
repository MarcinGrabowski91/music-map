package eu.gitcode.musicmap.data.place.model

import com.google.gson.annotations.SerializedName

data class Coordinates(
        @SerializedName("latitude") val latitude: String,
        @SerializedName("longitude") val longitude: String
) {
    fun getLatitude(): Double {
        return latitude.toDouble()
    }

    fun getLongitude(): Double {
        return longitude.toDouble()
    }
}