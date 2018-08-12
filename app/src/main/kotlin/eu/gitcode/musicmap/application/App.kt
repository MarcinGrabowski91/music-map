package eu.gitcode.musicmap.application

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
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
    }

    override fun applicationInjector(): AndroidInjector<App> =
            DaggerApplicationComponent.builder().create(this)
}
