package eu.gitcode.musicmap.application

import com.jakewharton.threetenabp.AndroidThreeTen
import com.mapbox.mapboxsdk.Mapbox
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import eu.gitcode.musicmap.BuildConfig
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var debugMetricsHelper: DebugMetricsHelper

    @Inject
    lateinit var rxJavaErrorHandler: RxJavaErrorHandler

    override fun onCreate() {
        super.onCreate()
        debugMetricsHelper.init(this)
        RxJavaPlugins.setErrorHandler(rxJavaErrorHandler)
        AndroidThreeTen.init(this)
        Mapbox.getInstance(this, BuildConfig.MAP_BOX_ACCESS_TOKEN)
    }

    override fun applicationInjector(): AndroidInjector<App> =
            DaggerApplicationComponent.builder().create(this)
}
