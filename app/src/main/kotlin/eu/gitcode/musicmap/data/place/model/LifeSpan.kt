package eu.gitcode.musicmap.data.place.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.Year
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException

data class LifeSpan(
        @SerializedName("ended") val ended: String?,
        @SerializedName("begin") val begin: String?
) {
    fun getBeginDate(): Int? {
        return begin?.let {
            return try {
                Year.parse(it, DateTimeFormatter.ofPattern("yyyy")).value
            } catch (e: DateTimeParseException) {
                try {
                    Year.parse(it, DateTimeFormatter.ofPattern("yyyy-MM")).value
                } catch (e: DateTimeParseException) {
                    try {
                        Year.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd")).value
                    } catch (e: DateTimeParseException) {
                        null
                    }
                }
            }
        }
    }
}