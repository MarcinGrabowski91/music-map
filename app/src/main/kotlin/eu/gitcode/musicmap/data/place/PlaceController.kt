package eu.gitcode.musicmap.data.place

import eu.gitcode.musicmap.data.place.model.Place
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
interface PlaceController {

    fun findPlaces(placeName: String): Single<List<Place>>
}