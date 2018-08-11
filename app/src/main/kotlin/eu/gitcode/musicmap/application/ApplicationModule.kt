package eu.gitcode.musicmap.application

import dagger.Module
import dagger.Provides
import eu.gitcode.musicmap.application.scope.AppScope

@Module
class ApplicationModule {

    @AppScope
    @Provides
    fun rxJavaErrorHandler(): RxJavaErrorHandler = RxJavaErrorHandlerImpl()
}
