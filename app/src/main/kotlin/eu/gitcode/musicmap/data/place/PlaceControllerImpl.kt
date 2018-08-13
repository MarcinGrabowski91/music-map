package eu.gitcode.musicmap.data.place

import eu.gitcode.musicmap.data.place.model.Place
import io.reactivex.Single

class PlaceControllerImpl constructor(private val placeApi: PlaceApi) : PlaceController {

    override fun findPlaces(placeName: String): Single<List<Place>> {
        return getFilteredPlaces(placeName, START_OFFSET, listOf())
    }

    private fun getFilteredPlaces(placeName: String, offset: Int, placesList: List<Place>)
            : Single<List<Place>> {
        return placeApi.findPlaces(placeName, PLACES_LIMIT_PER_REQUEST, offset)
                .flatMap { placeResponse ->
                    if (placeResponse.count < offset || offset >= OFFSET_LIMIT) {
                        filterPlaces(placesList)
                    } else {
                        getFilteredPlaces(placeName,
                                offset + PLACES_LIMIT_PER_REQUEST,
                                placesList.plus(placeResponse.places))
                    }
                }
    }

    private fun filterPlaces(placesList: List<Place>): Single<List<Place>> {
        return Single.just(placesList)
                .map { placeResponse -> placeResponse }
                .toFlowable()
                .flatMapIterable { places -> places }
                .filter { place ->
                    (place.lifeSpan?.getBeginDate() != null
                            && place.lifeSpan.getBeginDate()!! >= MIN_OPEN_YEAR)
                            || (place.area?.lifeSpan?.getBeginDate() != null
                            && place.area.lifeSpan.getBeginDate()!! >= MIN_OPEN_YEAR)
                }.toList()
    }

    companion object {
        private const val PLACES_LIMIT_PER_REQUEST = 20

        private const val MIN_OPEN_YEAR = 1990

        private const val START_OFFSET = 0

        // API has a limit of server requests. After 15 requests, server returns error 503.
        private const val OFFSET_LIMIT = PLACES_LIMIT_PER_REQUEST * 14
    }
}