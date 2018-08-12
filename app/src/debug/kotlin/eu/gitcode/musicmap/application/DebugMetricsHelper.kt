package eu.gitcode.musicmap.application

import android.content.Context
import com.facebook.stetho.Stetho
import eu.gitcode.musicmap.application.scope.AppScope
import timber.log.Timber
import javax.inject.Inject

/**
 * Helper class that initializes a set of debugging tools
 * for the debug build type and register crash manager for release type.
 * ## Debug type tools:
 * - Stetho
 * - Timber
 *
 * ## Release type tools:
 * - CrashManager
 */
@AppScope
class DebugMetricsHelper @Inject constructor() {

    internal fun init(context: Context) {

        // Stetho
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                        .build()
        )

        //Timber
        Timber.plant(Timber.DebugTree())
    }
}
