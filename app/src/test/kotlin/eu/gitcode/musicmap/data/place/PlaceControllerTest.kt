package eu.gitcode.musicmap.data.place

import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import eu.gitcode.musicmap.RxSchedulersOverrideRule
import eu.gitcode.musicmap.common.Consts.Companion.EXAMPLE_INT
import eu.gitcode.musicmap.common.Consts.Companion.EXAMPLE_STRING
import eu.gitcode.musicmap.data.place.model.PlaceResponse
import io.reactivex.Single
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
class ApiReferencedContentControllerTest {

    @Rule
    @JvmField
    internal val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    internal lateinit var placeApi: PlaceApi

    @InjectMocks
    private lateinit var placeController: PlaceControllerImpl

    @Before
    fun setUp() {
        placeController = PlaceControllerImpl(placeApi)
    }

    @Test
    fun `should get places from api when getting the places`() {
        //given
        whenever(placeApi.findPlaces(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
                .thenReturn(Single.just(PlaceResponse(EXAMPLE_STRING, EXAMPLE_INT, EXAMPLE_INT, listOf())))
        //when
        val testObserver = placeController.findPlaces(EXAMPLE_STRING).test()
        //then
        verify(placeApi, atLeastOnce()).findPlaces(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())
        testObserver.assertNoErrors()
    }
}