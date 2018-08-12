package eu.gitcode.musicmap.data.place.model

import com.google.gson.annotations.SerializedName

data class Place(
        @SerializedName("id") val id: String,
        @SerializedName("type") val type: String,
        @SerializedName("type-id") val typeId: String,
        @SerializedName("score") val score: Int,
        @SerializedName("name") val name: String,
        @SerializedName("address") val address: String,
        @SerializedName("coordinates") val coordinates: Coordinates,
        @SerializedName("area") val area: Area,
        @SerializedName("life-span") val lifeSpan: LifeSpan,
        @SerializedName("aliases") val aliases: List<Aliase>,
        @SerializedName("disambiguation") val disambiguation: String
)