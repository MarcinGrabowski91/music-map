package eu.gitcode.musicmap.application.module

import dagger.Module
import dagger.Provides
import eu.gitcode.musicmap.application.scope.AppScope
import eu.gitcode.musicmap.data.place.PlaceApi
import retrofit2.Retrofit

@Module
class ApiModule {

    @AppScope
    @Provides
    fun providePlaceApi(retrofit: Retrofit): PlaceApi =
            retrofit.create(PlaceApi::class.java)
}