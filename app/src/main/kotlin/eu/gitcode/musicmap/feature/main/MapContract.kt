package eu.gitcode.musicmap.feature.main

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mapbox.mapboxsdk.annotations.MarkerOptions

interface MapContract {

    interface View : MvpView {
        fun showMarkers(markerOptions: List<MarkerOptions>)

        fun showSearchError()

        fun showProgressBar()

        fun hideProgressBar()
    }

    interface Presenter : MvpPresenter<View> {
        fun getPlaces(placeName: String)
    }
}
