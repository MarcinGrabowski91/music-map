package eu.gitcode.musicmap.feature.main

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import eu.gitcode.musicmap.data.map.Marker

interface MapContract {

    interface View : MvpView {
        fun showMarkers(markers: List<Marker>)

        fun showSearchError()

        fun showProgressBar()

        fun hideProgressBar()
    }

    interface Presenter : MvpPresenter<View> {
        fun getPlaces(placeName: String)
    }
}
