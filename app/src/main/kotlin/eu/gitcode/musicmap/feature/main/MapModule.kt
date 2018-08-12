package eu.gitcode.musicmap.feature.main

import dagger.Binds
import dagger.Module
import eu.gitcode.musicmap.application.scope.FragmentScope

@Module
abstract class MapModule {

    @Binds
    @FragmentScope
    abstract fun bindPresenter(presenter: MapPresenter): MapContract.Presenter
}