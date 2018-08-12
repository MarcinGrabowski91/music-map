package eu.gitcode.musicmap.feature.main

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import eu.gitcode.musicmap.data.place.model.Place

interface MapContract {

    interface View : MvpView {
        fun showPlaces(places: List<Place>)
    }

    interface Presenter : MvpPresenter<View> {
        fun getPlaces(place: String)
    }
}
