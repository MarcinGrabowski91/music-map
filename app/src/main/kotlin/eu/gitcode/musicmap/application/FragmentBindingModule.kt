package eu.gitcode.musicmap.application

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import eu.gitcode.musicmap.application.scope.FragmentScope
import eu.gitcode.musicmap.feature.main.MapFragment
import eu.gitcode.musicmap.feature.main.MapModule

@Module(includes = [AndroidSupportInjectionModule::class])
internal abstract class FragmentBindingsModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [(MapModule::class)])
    internal abstract fun mapFragmentInjector(): MapFragment
}
