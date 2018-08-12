package eu.gitcode.musicmap.feature.main

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import eu.gitcode.musicmap.application.scope.FragmentScope
import eu.gitcode.musicmap.data.place.PlaceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class MapPresenter @Inject constructor(private val placeController: PlaceController)
    : MvpBasePresenter<MapContract.View>(), MapContract.Presenter {

    private var placesDisposable: Disposable? = null

    override fun getPlaces(place: String) {
        placesDisposable = placeController.findPlaces(place)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { it ->
                            val places = it
                            ifViewAttached { it.showPlaces(places) }
                        },
                        onError = { Timber.d("Error during loading places: $it") }
                )
    }

    override fun destroy() {
        super.destroy()
        if (placesDisposable?.isDisposed != false) return
        placesDisposable?.dispose()
    }
}
