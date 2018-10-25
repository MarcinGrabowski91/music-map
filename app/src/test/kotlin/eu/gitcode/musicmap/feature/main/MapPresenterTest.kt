package eu.gitcode.musicmap.feature.main

import android.location.Geocoder
import com.mapbox.mapboxsdk.geometry.LatLng
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import eu.gitcode.musicmap.RxSchedulersOverrideRule
import eu.gitcode.musicmap.common.Consts.Companion.EXAMPLE_INT
import eu.gitcode.musicmap.common.Consts.Companion.EXAMPLE_STRING
import eu.gitcode.musicmap.data.map.Marker
import eu.gitcode.musicmap.data.place.PlaceController
import eu.gitcode.musicmap.data.place.model.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
class MapPresenterTest {

    @Rule
    @JvmField
    internal val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    internal lateinit var placeController: PlaceController

    @Mock
    internal lateinit var view: MapContract.View

    @Mock
    internal lateinit var geocoder: Geocoder

    @InjectMocks
    private lateinit var mapPresenter: MapPresenter

    private val latitude: Double = 2.0
    private val longitude: Double = 1.0
    private val beginYear: Int = 1990

    val placesWithoutCoordinatesAndAddress =
            Place(EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_INT, EXAMPLE_STRING,
                    EXAMPLE_STRING, null,
                    Area(EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING,
                            LifeSpan(EXAMPLE_STRING, "1990")),
                    LifeSpan(EXAMPLE_STRING, "1990"),
                    listOf(Aliase(EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING,
                            EXAMPLE_STRING, "1990", EXAMPLE_STRING)), EXAMPLE_STRING)

    val placesWithCoordinates =
            Place(EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_INT, EXAMPLE_STRING,
                    EXAMPLE_STRING, Coordinates("2.0", "1.0"),
                    Area(EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING,
                            LifeSpan(EXAMPLE_STRING, "1990")),
                    LifeSpan(EXAMPLE_STRING, "1990"),
                    listOf(Aliase(EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING, EXAMPLE_STRING,
                            EXAMPLE_STRING, "1990", EXAMPLE_STRING)), EXAMPLE_STRING)

    @Before
    fun setUp() {
        mapPresenter.attachView(view)
    }

    @After
    fun cleanup() {
        mapPresenter.detachView()
    }

    @Test
    fun `should get empty list if there is no address or coordinates in found places`() {
        //given
        whenever(placeController.findPlaces(ArgumentMatchers.anyString()))
                .thenReturn(Single.just(listOf(placesWithoutCoordinatesAndAddress)))
        //when
        mapPresenter.getPlaces(EXAMPLE_STRING)
        //then
        verify(view).showMarkers(listOf())
    }

    @Test
    fun `should get object in returned list if there are coordinates in found places`() {
        //given
        whenever(placeController.findPlaces(ArgumentMatchers.anyString()))
                .thenReturn(Single.just(listOf(placesWithCoordinates)))
        //when
        mapPresenter.getPlaces(EXAMPLE_STRING)
        //then
        verify(view).showMarkers(listOf(Marker(LatLng(latitude, longitude),
                EXAMPLE_STRING, beginYear)))
    }

    @Test
    fun `should show progress bar after search starts`() {
        //given
        whenever(placeController.findPlaces(ArgumentMatchers.anyString()))
                .thenReturn(Single.just(listOf(placesWithCoordinates)))
        //when
        mapPresenter.getPlaces(EXAMPLE_STRING)
        //then
        verify(view).showProgressBar()
    }

    @Test
    fun `should hide progress bar after search ends`() {
        //given
        whenever(placeController.findPlaces(ArgumentMatchers.anyString()))
                .thenReturn(Single.just(listOf(placesWithCoordinates)))
        //when
        mapPresenter.getPlaces(EXAMPLE_STRING)
        //then
        verify(view).hideProgressBar()
    }

    @Test
    fun `should show search error when there is an error during loading places`() {
        //given
        whenever(placeController.findPlaces(ArgumentMatchers.anyString()))
                .thenReturn(Single.error(Throwable()))
        //when
        mapPresenter.getPlaces(EXAMPLE_STRING)
        //then
        verify(view).showSearchError()
    }
}