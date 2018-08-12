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
                    (t.lifeSpan?.getBeginDate() != null
                            && t.lifeSpan.getBeginDate()!! >= MIN_OPEN_YEAR)
                            || (t.area?.lifeSpan?.getBeginDate() != null
                            && t.area.lifeSpan.getBeginDate()!! >= MIN_OPEN_YEAR)
                }.toList()
    }

    companion object {
        private const val PLACES_LIMIT = 400

        private const val MIN_OPEN_YEAR = 1990
    }
}