package eu.gitcode.musicmap.feature.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import dagger.android.support.AndroidSupportInjection
import eu.gitcode.musicmap.R
import eu.gitcode.musicmap.common.extensions.hideKeyboard
import eu.gitcode.musicmap.common.extensions.isNetworkActive
import eu.gitcode.musicmap.data.map.Marker
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.map_fragment.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MapFragment : MvpFragment<MapContract.View, MapContract.Presenter>(),
        MapContract.View {

    @Inject
    lateinit var mapPresenter: MapPresenter

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        sendImg.setOnClickListener { _ -> findPlaces() }
        searchEdit.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                findPlaces()
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun showMarkers(markers: List<Marker>) {
        mapView.getMapAsync { mapBoxMap ->
            compositeDisposable.clear()
            mapBoxMap.clear()
            if (!markers.isEmpty()) {
                for (marker in markers) {
                    addMarker(mapBoxMap, marker)
                }
                moveCamera(mapBoxMap, markers[0].coordinates)
                Toast.makeText(context, R.string.search_completed, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, R.string.search_no_results, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showSearchError() {
        Toast.makeText(context, R.string.search_error, Toast.LENGTH_SHORT).show()
    }

    override fun showTooManyRequestsError() {
        Toast.makeText(context, R.string.too_many_requests, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun createPresenter(): MapContract.Presenter = mapPresenter

    private fun findPlaces() {
        activity?.hideKeyboard()
        if (context != null && !context!!.isNetworkActive()) {
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
        } else {
            if (progressBar.visibility != View.VISIBLE) {
                presenter.getPlaces(searchEdit.text.toString())
            }
        }
    }

    private fun addMarker(mapBoxMap: MapboxMap, marker: Marker) {
        val secondsToRemove = marker.beginYear - MIN_OPEN_YEAR
        val markerOptions = MarkerOptions()
                .setPosition(marker.coordinates)
                .setTitle(marker.name)
                .setSnippet(resources.getString(R.string.open_year, marker.beginYear))

        val addedMarker = mapBoxMap.addMarker(markerOptions)
        compositeDisposable += Observable.just(addedMarker)
                .delay(secondsToRemove.toLong(), TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = { mapBoxMap.removeMarker(it) },
                        onError = { Timber.d("Error during removing marker: $it") })

    }

    private fun moveCamera(mapBoxMap: MapboxMap, coordinates: LatLng) {
        val cameraPosition = CameraPosition.Builder()
                .target(coordinates)
                .zoom(ZOOM_AFTER_MAP_MOVE)
                .build()
        mapBoxMap.moveCamera { cameraPosition }
    }

    companion object {
        val TAG = MapFragment::class.java.simpleName!!

        private const val ZOOM_AFTER_MAP_MOVE = 3.0

        private const val MIN_OPEN_YEAR = 1990

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}