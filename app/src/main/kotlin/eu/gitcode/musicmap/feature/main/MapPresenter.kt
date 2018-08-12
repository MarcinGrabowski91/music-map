package eu.gitcode.musicmap.feature.main

import android.location.Geocoder
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import eu.gitcode.musicmap.application.scope.FragmentScope
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

    private fun convertToMarkers(places: List<Place>): List<MarkerOptions> {
        val markers = ArrayList<MarkerOptions>()
        for (place in places) {
            if (place.coordinates != null) {
                val latLng = LatLng(place.coordinates.getLatitude(), place.coordinates.getLongitude())
                markers.add(MarkerOptions().position(latLng).title(place.name))
            } else if (place.address != null) {
                geocoder.getFromLocationName(place.address, ADDRESS_SEARCH_MAX_RESULTS)
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
