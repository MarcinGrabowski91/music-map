package eu.gitcode.musicmap.application

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import eu.gitcode.musicmap.application.module.ApiModule
import eu.gitcode.musicmap.application.module.NetworkModule
import eu.gitcode.musicmap.application.scope.AppScope

@AppScope
@Component(
        modules = [
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            ApplicationModule::class,
            ApiModule::class,
            NetworkModule::class,
            FragmentBindingsModule::class
        ]
)
internal interface ApplicationComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
