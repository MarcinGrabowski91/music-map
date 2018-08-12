package eu.gitcode.musicmap.data.place.model

import com.google.gson.annotations.SerializedName

data class Aliase(
        @SerializedName("sort-name") val sortName: String,
        @SerializedName("name") val name: String,
        @SerializedName("locale") val locale: String,
        @SerializedName("type") val type: Any?,
        @SerializedName("primary") val primary: Any?,
        @SerializedName("begin-date") val beginDate: Any?,
        @SerializedName("end-date") val endDate: Any?
)