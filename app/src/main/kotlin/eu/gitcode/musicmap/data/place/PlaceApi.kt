package eu.gitcode.musicmap.data.place

import eu.gitcode.musicmap.data.place.model.PlaceResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceApi {

    @GET("place")
    fun findPlaces(
            @Query("query") query: String, @Query("limit") limit: Int,
            @Query("fmt") format: String = "json"
    ): Single<PlaceResponse>
}