package eu.gitcode.musicmap.data.place.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
        @SerializedName("created") val created: String,
        @SerializedName("count") val count: Int,
        @SerializedName("offset") val offset: Int,
        @SerializedName("places") val places: List<Place>
)