package eu.gitcode.musicmap.application

import io.reactivex.exceptions.UndeliverableException
import timber.log.Timber

class RxJavaErrorHandlerImpl : RxJavaErrorHandler() {

    override fun handleUndeliverableException(undeliverableException: UndeliverableException) {
        Timber.e(undeliverableException)
    }

}
