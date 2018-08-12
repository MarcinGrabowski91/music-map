package eu.gitcode.musicmap.data.place

import eu.gitcode.musicmap.data.place.model.Place
import io.reactivex.Single

class PlaceControllerImpl constructor(private val placeApi: PlaceApi) : PlaceController {
    override fun findPlaces(place: String): Single<List<Place>> {
        return placeApi
                .findPlaces(place, PLACES_LIMIT)
                .map { t -> t.places }
                .toFlowable()
                .flatMapIterable { t -> t }
                .filter { t ->
                    (t.lifeSpan.getBeginDate() != null && t.lifeSpan.getBeginDate()!! >= 1990)
                            || (t.area.lifeSpan.getBeginDate() != null
                            && t.area.lifeSpan.getBeginDate()!! >= 1990)
                }.toList()
    }

    companion object {
        private const val PLACES_LIMIT = 400
    }
}