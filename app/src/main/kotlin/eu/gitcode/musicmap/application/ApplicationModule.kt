package eu.gitcode.musicmap.application

import dagger.Module
import dagger.Provides
import eu.gitcode.musicmap.application.scope.AppScope
import eu.gitcode.musicmap.data.place.PlaceApi
import eu.gitcode.musicmap.data.place.PlaceController
import eu.gitcode.musicmap.data.place.PlaceControllerImpl

@Module
class ApplicationModule {

    @AppScope
    @Provides
    fun rxJavaErrorHandler(): RxJavaErrorHandler = RxJavaErrorHandlerImpl()

    @AppScope
    @Provides
    fun providePlaceController(placeApi: PlaceApi): PlaceController = PlaceControllerImpl(placeApi)
}
