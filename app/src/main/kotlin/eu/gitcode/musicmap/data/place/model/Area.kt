package eu.gitcode.musicmap.data.place.model

import com.google.gson.annotations.SerializedName

data class Area(
        @SerializedName("id") val id: String,
        @SerializedName("type") val type: String,
        @SerializedName("type-id") val typeId: String,
        @SerializedName("name") val name: String,
        @SerializedName("sort-name") val sortName: String,
        @SerializedName("life-span") val lifeSpan: LifeSpan?
)