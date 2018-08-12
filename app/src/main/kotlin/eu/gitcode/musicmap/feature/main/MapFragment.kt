package eu.gitcode.musicmap.feature.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import dagger.android.support.AndroidSupportInjection
import eu.gitcode.musicmap.R
import eu.gitcode.musicmap.data.place.model.Place
import timber.log.Timber
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
        presenter.getPlaces("Poland") //TODO implement search logic
    }

    override fun showPlaces(places: List<Place>) {
        Timber.d(places.toString()) //TODO implement showing places logic
    }

    override fun createPresenter(): MapContract.Presenter = mapPresenter

    companion object {
        val TAG = MapFragment::class.java.simpleName!!

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}