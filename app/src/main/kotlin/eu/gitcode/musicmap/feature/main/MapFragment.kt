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
import dagger.android.support.AndroidSupportInjection
import eu.gitcode.musicmap.R
import eu.gitcode.musicmap.common.extensions.hideKeyboard
import eu.gitcode.musicmap.common.extensions.isNetworkActive
import kotlinx.android.synthetic.main.map_fragment.*
import javax.inject.Inject


class MapFragment : MvpFragment<MapContract.View, MapContract.Presenter>(),
        MapContract.View {

    @Inject
    lateinit var mapPresenter: MapPresenter

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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun showMarkers(markerOptions: List<MarkerOptions>) {
        mapView.getMapAsync { mapBoxMap ->
            mapBoxMap.clear()
            if (!markerOptions.isEmpty()) {
                for (marker in markerOptions) {
                    mapBoxMap.addMarker(marker)
                }
                val coordinatesToMove = markerOptions[0].position
                val cameraPosition = CameraPosition.Builder()
                        .target(coordinatesToMove)
                        .zoom(ZOOM_AFTER_MAP_MOVE)
                        .build()
                mapBoxMap.moveCamera { cameraPosition }
                Toast.makeText(context, R.string.search_completed, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, R.string.search_no_results, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showSearchError() {
        Toast.makeText(context, R.string.search_error, Toast.LENGTH_SHORT).show()
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
            presenter.getPlaces(searchEdit.text.toString())
        }
    }

    companion object {
        val TAG = MapFragment::class.java.simpleName!!

        private const val ZOOM_AFTER_MAP_MOVE = 3.0

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}