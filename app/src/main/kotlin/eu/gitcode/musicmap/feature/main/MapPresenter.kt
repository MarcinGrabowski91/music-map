package eu.gitcode.musicmap.feature.main

import android.location.Geocoder
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.mapbox.mapboxsdk.geometry.LatLng
import eu.gitcode.musicmap.application.scope.FragmentScope
import eu.gitcode.musicmap.data.map.Marker
import eu.gitcode.musicmap.data.place.PlaceController
import eu.gitcode.musicmap.data.place.model.Place
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class MapPresenter @Inject constructor(private val placeController: PlaceController,
                                       private val geocoder: Geocoder)
    : MvpBasePresenter<MapContract.View>(), MapContract.Presenter {

    private var placesDisposable: Disposable? = null

    override fun getPlaces(placeName: String) {
        placesDisposable = placeController.findPlaces(placeName)
                .subscribeOn(Schedulers.io())
                .map { convertToMarkers(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { ifViewAttached { it.showProgressBar() } }
                .doAfterTerminate { ifViewAttached { it.hideProgressBar() } }
                .subscribeBy(
                        onSuccess = { it ->
                            val markers = it
                            ifViewAttached { it.showMarkers(markers) }
                        },
                        onError = {
                            Timber.d("Error during loading places: $it")
                            ifViewAttached { view -> view.showSearchError() }
                        }
                )
    }

    private fun convertToMarkers(places: List<Place>): List<Marker> {
        val markers = ArrayList<Marker>()
        for (place in places) {
            val beginYear = if (place.lifeSpan?.begin == null) {
                place.area!!.lifeSpan!!.getBeginDate()!!
            } else {
                place.lifeSpan.getBeginDate()!!
            }
            if (place.coordinates != null) {
                val coordinates = LatLng(place.coordinates.getLatitude(),
                        place.coordinates.getLongitude())
                markers.add(Marker(coordinates, place.name, beginYear))
            } else if (place.address != null) {
                val addresses = geocoder.getFromLocationName(place.address, ADDRESS_SEARCH_MAX_RESULTS)
                if (addresses != null && !addresses.isEmpty()) {
                    val coordinates = LatLng(addresses[0].latitude, addresses[0].longitude)
                    markers.add(Marker(coordinates, place.name, beginYear))
                }
            }
        }
        return markers
    }

    override fun destroy() {
        super.destroy()
        if (placesDisposable?.isDisposed != false) return
        placesDisposable?.dispose()
    }

    companion object {
        private const val ADDRESS_SEARCH_MAX_RESULTS = 1
    }
}
